package vn.edu.tdtu.finalexam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Base64;
import java.util.List;

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.NoteItemHolder> {
    private final SelectRecycleViewInterface selectRecycleViewInterface;
    private Context mContext;
    private List<NoteItem> noteItemList;

    public NoteItemAdapter(Context mContext, SelectRecycleViewInterface selectRecycleViewInterface) {
        this.mContext = mContext;
        this.selectRecycleViewInterface = selectRecycleViewInterface;
    }

    public void setData(List<NoteItem> list) {
        this.noteItemList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteItemHolder(view, selectRecycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemHolder holder, int position) {
        NoteItem noteItem = noteItemList.get(position);
        if(noteItem == null) return;

        holder.typeTV.setText(noteItem.getType());
        holder.tileTV.setText(noteItem.getTitle());
        holder.timeTV.setText(noteItem.getTime().toString());

        if(noteItem.getType().equals("Note")) return;

        String base64 = noteItem.getData();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            byte[] bytes = Base64.getDecoder().decode(base64);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        if(noteItemList != null) {
            return noteItemList.size();
        }
        return 0;
    }

    public static class NoteItemHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView typeTV, tileTV, timeTV;

        public NoteItemHolder(@NonNull View itemView, SelectRecycleViewInterface selectRecycleViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardImage);
            typeTV = itemView.findViewById(R.id.cardType);
            tileTV = itemView.findViewById(R.id.cardTitle);
            timeTV = itemView.findViewById(R.id.cardTime);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectRecycleViewInterface == null) return;
                    int pos = getAdapterPosition();

                    if(pos == RecyclerView.NO_POSITION) return;
                    selectRecycleViewInterface.onItemClick(pos);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(selectRecycleViewInterface == null) return false;
                    int pos = getAdapterPosition();

                    if(pos == RecyclerView.NO_POSITION) return false;
                    selectRecycleViewInterface.onDeleteClick(pos);
                    return true;
                }
            });
        }
    }
}
