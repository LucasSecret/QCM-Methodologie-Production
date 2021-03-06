package fr.iutmindfuck.qcmiutlyon1.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;


public class QuestionEditionActivity extends AppCompatActivity {

    private QuestionSQLHandler questionSQLHandler;
    private Question question;
    private int idMCQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra();
        questionSQLHandler = new QuestionSQLHandler(new SQLServices(this));

        setContentView(R.layout.activity_question_edition);
        setSupportActionBar((Toolbar) findViewById(R.id.question_edition_toolbar));
    }
    private void getExtra() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            question = (Question) extra.getSerializable("question");
            idMCQ = extra.getInt("idMCQ");
        }
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuestionListActivity.class);
                intent.putExtra("idMCQ", idMCQ);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (question == null)
            return;

        initFormField();
        ((Button) findViewById(R.id.question_edition_submit)).setText(R.string.mcq_edition_edit);
    }

    private void initFormField() {
        setQuestionTitle(question.getTitle());

        if (question.getAnswers() != null)
            setAnswers(question.getAnswers());
    }

    private void setQuestionTitle(String title) {
        ((EditText) findViewById(R.id.question_edition_title_input)).setText(title);
    }
    @SuppressLint("InflateParams")
    private void setAnswers(ArrayList<Answer> answers) {
        LinearLayout parent = findViewById(R.id.question_edition_answer_container);

        for (Answer answer : answers) {
            View custom = getLayoutInflater().inflate(R.layout.answer_edition_section, null);

            ((EditText) custom.findViewById(R.id.answer_title)).setText(answer.getTitle());
            if (answer.isRight())
            {
                ((CheckBox) custom.findViewById(R.id.answer_is_true)).setChecked(true);
            }
            else
            {
                ((CheckBox) custom.findViewById(R.id.answer_is_true)).setChecked(false);
            }

            parent.addView(custom);
        }
    }

    /* ******************/
    /* User Interaction */
    /********************/

    public void addAnswer(View view) {
        LinearLayout layout = findViewById(R.id.question_edition_answer_container);
        getLayoutInflater().inflate(R.layout.answer_edition_section, layout);
    }
    public void removeAnswer(View view) {
        LinearLayout layout = findViewById(R.id.question_edition_answer_container);
        layout.removeView((View) view.getParent());
    }
    public void submitQuestion(View view) {
        String title = getQuestionTitle();
        ArrayList<Answer> answers = getQuestionAnswers();

        if (title.isEmpty() || !Answer.checkAnswersValidity(answers)) {
            displayErrorToast();
            return;
        }

        if (question == null) {
            questionSQLHandler
                    .createOrReplaceQuestion(new Question(null, title, answers), idMCQ);
        }
        else
        {
            questionSQLHandler
                    .createOrReplaceQuestion(new Question(question.getId(), title, answers), idMCQ);
        }

        Intent intent = new Intent(this, QuestionListActivity.class);
        intent.putExtra("idMCQ", idMCQ);
        startActivity(intent);
    }

    /* Get Value from Question Form */
    private String getQuestionTitle() {
        return ((EditText) findViewById(R.id.question_edition_title_input)).getText().toString();
    }
    private ArrayList<Answer> getQuestionAnswers() {
        ArrayList<Answer> answers = new ArrayList<>();

        LinearLayout layout = findViewById(R.id.question_edition_answer_container);
        for (int i = 0; i < layout.getChildCount(); i++) {
            LinearLayout answerSection = (LinearLayout) layout.getChildAt(i);
            answers.add(new Answer(
                    ((EditText) answerSection.getChildAt(1)).getText().toString(),
                    ((CheckBox) answerSection.getChildAt(0)).isChecked())
            );
        }

        return answers;
    }

    /* Toast */
    private void displayErrorToast() {
        Context context = this.getApplicationContext();

        Toast toast = Toast.makeText(context, getString(R.string.question_invalid_submission),
                                              Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }

}
