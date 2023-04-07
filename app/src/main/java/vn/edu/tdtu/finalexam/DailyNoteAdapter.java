package vn.edu.tdtu.finalexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyNoteAdapter  extends RecyclerView.Adapter<DailyNoteAdapter.DailyNoteHolder> {
    //private ArrayList<String> dayOfMonths;

    @NonNull
    @Override
    public DailyNoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new DailyNoteAdapter.DailyNoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyNoteHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class DailyNoteHolder extends RecyclerView.ViewHolder {
        private TextView timeTV, dailyContent;
        private CheckBox checkBox;
        public DailyNoteHolder(@NonNull View itemView) {
            super(itemView);

            timeTV = itemView.findViewById(R.id.timeView);
            dailyContent = itemView.findViewById(R.id.dailyContent);
            checkBox = itemView.findViewById(R.id.checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    System.out.println(b);
                }
            });
        }
    }
}
