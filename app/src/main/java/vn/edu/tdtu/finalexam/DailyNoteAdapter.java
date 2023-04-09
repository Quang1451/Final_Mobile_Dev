package vn.edu.tdtu.finalexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DailyNoteAdapter  extends RecyclerView.Adapter<DailyNoteAdapter.DailyNoteHolder> {
    private List<DailyNoteItem> dailyNoteList;

    public void setDailyNoteList(List<DailyNoteItem> dailyNoteList) {
        this.dailyNoteList = dailyNoteList;
    }

    private final SelectRecycleViewInterface selectRecycleViewInterface;

    public DailyNoteAdapter(List<DailyNoteItem> dailyNoteList, SelectRecycleViewInterface selectRecycleViewInterface) {
        this.dailyNoteList = dailyNoteList;
        this.selectRecycleViewInterface = selectRecycleViewInterface;
    }

    @NonNull
    @Override
    public DailyNoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_note_item, parent, false);
        return new DailyNoteAdapter.DailyNoteHolder(view, selectRecycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyNoteHolder holder, int position) {
        DailyNoteItem dailyNoteItem = dailyNoteList.get(position);

        if(dailyNoteItem == null) return;

        holder.timeTextView.setText(dailyNoteItem.getTime());
        holder.dailyContent.setText(dailyNoteItem.getData());
    }

    @Override
    public int getItemCount() {
        if(dailyNoteList != null) {
            return dailyNoteList.size();
        }
        return 0;
    }

    public static class DailyNoteHolder extends RecyclerView.ViewHolder {
        private TextView timeTextView, dailyContent;
        private CheckBox checkBox;
        public DailyNoteHolder(@NonNull View itemView, SelectRecycleViewInterface selectRecycleViewInterface) {
            super(itemView);

            timeTextView = itemView.findViewById(R.id.timeView);
            dailyContent = itemView.findViewById(R.id.dailyContent);
            checkBox = itemView.findViewById(R.id.checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    System.out.println(b);
                }
            });

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
