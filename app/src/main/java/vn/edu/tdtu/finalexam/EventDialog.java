package vn.edu.tdtu.finalexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EventDialog extends AppCompatDialogFragment {
    private EventDialogInterface eventDialogInterface;
    private TextView title;
    private NumberPicker hourNumber, minuteNumber;
    private EditText eventContent;
    private String type;
    private DailyNoteItem dailyNoteItem;

    public EventDialog() {
        this.type = "Add";
    }

    public EventDialog(String type, DailyNoteItem dailyNoteItem) {
        this.type = type;
        this.dailyNoteItem = dailyNoteItem;
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


        switch (type) {
            case "Add":
                hourNumber.setValue(0);
                minuteNumber.setValue(0);
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
                                String time = String.format("%02d", hourNumber.getValue())+":"+String.format("%02d", minuteNumber.getValue());
                                String content = eventContent.getText().toString().trim();
                                if(content.isEmpty() || content.length() < 1) {
                                    Toast.makeText(getActivity(),"Chưa nhập hoạt đông", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                eventDialogInterface.addEventValue(time,content);
                            }
                        });
                break;
            case "Edit":
                String[] time = dailyNoteItem.getTime().split(":");
                hourNumber.setValue(Integer.parseInt(time[0]));
                minuteNumber.setValue(Integer.parseInt(time[1]));
                eventContent.setText(dailyNoteItem.getData());

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
                                String time = String.format("%02d", hourNumber.getValue())+":"+String.format("%02d", minuteNumber.getValue());
                                String content = eventContent.getText().toString().trim();
                                if(content.isEmpty() || content.length() < 1) {
                                    Toast.makeText(getActivity(),"Chưa nhập hoạt đông", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                eventDialogInterface.editEventValue(dailyNoteItem.getId(), time, content);
                            }
                        });
                break;
        }
        return addDialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            eventDialogInterface = (EventDialogInterface) context;
        }catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }

    public interface EventDialogInterface {
        void addEventValue(String time, String content);
        void editEventValue(String id, String time, String content);
    }
}
