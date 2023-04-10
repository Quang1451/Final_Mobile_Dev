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

        if(cell.getNumberEvent() > 0) {
            holder.numberEvent.setVisibility(View.VISIBLE);
            holder.numberEvent.setText(cell.getNumberEvent()+" EVENT");
            holder.numberEvent.setTextColor(Color.WHITE);
            switch (cell.getNumberEvent()) {
                case 1:
                    holder.numberEvent.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    holder.numberEvent.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    holder.numberEvent.setBackgroundColor(Color.RED);
                    break;
            }
        }
        else {
            holder.numberEvent.setVisibility(View.INVISIBLE);
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
        private TextView dayOfMonth, numberEvent;

        public CalendarViewHolder(@NonNull View itemView, SelectRecycleViewInterface selectRecycleViewInterface) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            numberEvent = itemView.findViewById(R.id.numberEvent);
            numberEvent.setVisibility(View.INVISIBLE);
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
