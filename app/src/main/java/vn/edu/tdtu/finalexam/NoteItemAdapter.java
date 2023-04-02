package vn.edu.tdtu.finalexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.NoteItemHolder> {
    private List<NoteItem> noteItemList;

    public void setData(List<NoteItem> list) {
        this.noteItemList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemHolder holder, int position) {
        NoteItem noteItem = noteItemList.get(position);
        if(noteItem == null) return;

        holder.typeTV.setText(noteItem.getType());
        holder.tileTV.setText(noteItem.getTitle());
        holder.timeTV.setText(noteItem.getTime().toString());

        if(noteItem.getType().equals("Note")) {
            String base64 = noteItem.getData();
            //holder.imageView.setImageBitmap();
        }



    }

    @Override
    public int getItemCount() {
        if(noteItemList != null) {
            return noteItemList.size();
        }
        return 0;
    }

    public class NoteItemHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView typeTV, tileTV, timeTV;

        public NoteItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardImage);
            typeTV = itemView.findViewById(R.id.cardType);
            tileTV = itemView.findViewById(R.id.cardTitle);
            timeTV = itemView.findViewById(R.id.cardTime);
        }
    }
}
