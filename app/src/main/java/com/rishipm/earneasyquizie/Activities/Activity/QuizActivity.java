package com.rishipm.earneasyquizie.Activities.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rishipm.earneasyquizie.Activities.Fragments.HomeFragment;
import com.rishipm.earneasyquizie.Activities.Model.Question;
import com.rishipm.earneasyquizie.R;
import com.rishipm.earneasyquizie.databinding.ActivityQuizBinding;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;
    ArrayList<Question> questions;
    int index = 0;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAns = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

        final String catId = getIntent().getStringExtra("catId");

//        to get Random Questions
        Random random = new Random();
        int randIndex = random.nextInt(10);

        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index", randIndex)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() < 5) {

                            database.collection("categories")
                                    .document(catId)
                                    .collection("questions")
                                    .whereLessThanOrEqualTo("index", randIndex)
                                    .orderBy("index")
                                    .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                                Question question = snapshot.toObject(Question.class);
                                                questions.add(question);
                                            }
                                            setNextQue();

                                        }
                                    });
                        } else {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                            setNextQue();

                        }
                    }
                });


        resetTimer();

    }

    public void setNextQue() {
        if (index < questions.size()) {

            if (timer != null) {
                timer.cancel();
            }


            timer.start();

            binding.queCounterQuiz.setText(String.format("%d/%d", (index + 1), questions.size()));

            question = questions.get(index);
            binding.questionQuiz.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());
        }
    }

    void showAns() {
        if (question.getAnswer().equals(binding.option1.getText().toString())) {
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else if (question.getAnswer().equals(binding.option2.getText().toString())) {
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else if (question.getAnswer().equals(binding.option3.getText().toString())) {
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else if (question.getAnswer().equals(binding.option4.getText().toString())) {
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
    }

    public void checkAns(TextView textView) {

        String selectedAns = textView.getText().toString();
        if (selectedAns.equals(question.getAnswer())) {
            correctAns++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
            showAns();
        }
    }

    void reset() {
        binding.option1.setBackground(getResources().getDrawable(R.drawable.options_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.options_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.options_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.options_unselected));
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:

                if (timer != null) {
                    timer.cancel();
                }
                TextView selectedOption = (TextView) view;
                checkAns(selectedOption);
                break;

            case R.id.btn_next:
                reset();
                index++;
                if (index == questions.size()) {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("correct", correctAns);
                    intent.putExtra("total", questions.size());
                    startActivity(intent);
                    finish();
                } else {
                    setNextQue();
                }
                break;

            case R.id.btn_quit:
                // Go to the home fragment
                Intent intent = new Intent(QuizActivity.this, HomeFragment.class);
                startActivity(intent);
                finish();
                break;


        }
    }
    void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timerQuiz.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                // Check if the timer has finished before setting the next question
                if (index < questions.size()) {
                    setNextQue();
                } else {
                    // If the timer has finished and there are no more questions, go to the result activity
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("correct", correctAns);
                    intent.putExtra("total", questions.size());
                    startActivity(intent);
                    finish();
                }
            }
        };
    }
}