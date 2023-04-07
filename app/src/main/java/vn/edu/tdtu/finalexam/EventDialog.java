package vn.edu.tdtu.finalexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EventDialog extends AppCompatDialogFragment {
    private TextView title;
    private NumberPicker hourNumber, minuteNumber;
    private EditText eventContent;
    private String type;
    private String content;
    private int hour, minute;

    public EventDialog() {
        this.type = "Add";
        this.hour = 0;
        this.minute = 0;
        this.content= "";
    }

    public EventDialog(String type, int hour, int minute, String content) {
        this.type = type;
        this.hour = hour;
        this.minute = minute;
        this.content = content;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder addDialog = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.event_dailog, null);
        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        };
        title = view.findViewById(R.id.titleTV);
        hourNumber = view.findViewById(R.id.hourNumber);
        minuteNumber = view.findViewById(R.id.minuteNumber);
        eventContent = view.findViewById(R.id.eventContent);

        hourNumber.setMinValue(0);
        hourNumber.setMaxValue(23);

        minuteNumber.setMinValue(0);
        minuteNumber.setMaxValue(59);

        hourNumber.setFormatter(formatter);
        minuteNumber.setFormatter(formatter);

        if(hour < 24) {
            hourNumber.setValue(hour);
        }
        else
        {
            hourNumber.setValue(0);
        }

        if(minute < 60) {
            minuteNumber.setValue(minute);
        }
        else
        {
            minuteNumber.setValue(0);
        }

        if(!content.isEmpty()) {
            eventContent.setText(content);
        }

        switch (type) {
            case "Add":
                title.setText("Thêm hoạt động");
                addDialog.setView(view)
                        .setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                break;
            case "Edit":
                title.setText("Chỉnh sửa hoạt động");
                addDialog.setView(view)
                        .setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Chỉnh sửa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                break;
        }
        return addDialog.create();
    }
}
