package fr.iutmindfuck.qcmiutlyon1.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Question;

public class QuestionStudentListAdapter extends ArrayAdapter<Question> {

    private final List<Question> questionsList;
    private final Context context;
    private final int idMCQ;

    public QuestionStudentListAdapter(Context context, int idMCQ, List<Question> questionsList)
    {
        super(context, 0, questionsList);

        this.idMCQ = idMCQ;
        this.questionsList = questionsList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.default_list_item, parent, false);
        }

        initView(convertView, position);
        setModifyClickListener(convertView, position);

        return convertView;
    }

    private void initView(View convertView, final int position) {
        DefaultItemViewHolder viewHolder = (DefaultItemViewHolder) convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new DefaultItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final Question question = getItem(position);
        if (question != null) {
            viewHolder.title.setText(String.format(context.getString(R.string.question_id_display), position + 1));
            viewHolder.description.setText(question.getTitle());

            // TODO: Change secondItem depending on submission.
        }
    }

    private void setModifyClickListener(final View convertView, final int position) {
        convertView.findViewById(R.id.list_item_first_interaction).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*  Wait for implementation

                        Question question = questionsList.get(position);

                        Intent intent = new Intent(context, QuestionAnswerActivity.class);
                        intent.putExtra("question", question);
                        intent.putExtra("idMCQ", idMCQ);
                        context.startActivity(intent);
                        */
                    }
                }
        );
    }
}