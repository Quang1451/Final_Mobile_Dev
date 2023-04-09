package vn.edu.tdtu.finalexam;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private ArrayList<CalendarCell> dayOfMonths;

    public void setDayOfMonths(ArrayList<CalendarCell> dayOfMonths) {
        this.dayOfMonths = dayOfMonths;
    }

    private final SelectRecycleViewInterface selectRecycleViewInterface;

    public CalendarAdapter(SelectRecycleViewInterface selectRecycleViewInterface) {
        this.selectRecycleViewInterface = selectRecycleViewInterface;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarAdapter.CalendarViewHolder(view, selectRecycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        CalendarCell cell = dayOfMonths.get(position);
        holder.dayOfMonth.setText(cell.getDay());
        switch (cell.getColor()) {
            case "white":
                holder.dayOfMonth.setTextColor(Color.BLACK);
                holder.itemView.setBackgroundColor(Color.WHITE);
                break;
            case "red":
                holder.dayOfMonth.setTextColor(Color.WHITE);
                holder.itemView.setBackgroundColor(Color.RED);
                break;
            case "blue":
                holder.dayOfMonth.setTextColor(Color.WHITE);
                holder.itemView.setBackgroundColor(Color.BLUE);
                break;
            case "yellow":
                holder.dayOfMonth.setTextColor(Color.WHITE);
                holder.itemView.setBackgroundColor(Color.YELLOW);
                break;
            case "green":
                holder.dayOfMonth.setTextColor(Color.WHITE);
                holder.itemView.setBackgroundColor(Color.GREEN);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(dayOfMonths != null) {
            return dayOfMonths.size();
        }
        return 0;
    }


    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        private TextView dayOfMonth;

        public CalendarViewHolder(@NonNull View itemView, SelectRecycleViewInterface selectRecycleViewInterface) {
            super(itemView);
            dayOfMonth =itemView.findViewById(R.id.cellDayText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectRecycleViewInterface == null) return;
                    int pos = getAdapterPosition();

                    if(pos == RecyclerView.NO_POSITION) return;
                    selectRecycleViewInterface.onItemClick(pos);
                }
            });
        }
    }
}
