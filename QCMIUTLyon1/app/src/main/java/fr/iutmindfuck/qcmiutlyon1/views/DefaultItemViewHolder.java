package fr.iutmindfuck.qcmiutlyon1.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fr.iutmindfuck.qcmiutlyon1.R;


class DefaultItemViewHolder {
    final TextView title;
    final TextView description;
    final ImageView list_item_first_interaction;
    final ImageView list_item_second_interaction;

    DefaultItemViewHolder(View convertView) {
        title = convertView.findViewById(R.id.list_item_title);
        description = convertView.findViewById(R.id.list_item_description);
        list_item_first_interaction = convertView.findViewById(R.id.list_item_first_interaction);
        list_item_second_interaction = convertView.findViewById(R.id.list_item_second_interaction);
    }
}