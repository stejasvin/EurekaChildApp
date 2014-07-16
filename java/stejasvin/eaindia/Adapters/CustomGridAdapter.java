package stejasvin.eaindia.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import stejasvin.eaindia.Objects.Skill;
import stejasvin.eaindia.Objects.Student;
import stejasvin.eaindia.R;
import stejasvin.eaindia.Utils.Utilities;

/**
 * Created by stejasvin on 7/13/2014.
 */
public class CustomGridAdapter extends BaseAdapter {

    private Context context;
    private String[] items;
    LayoutInflater inflater;
    ArrayList<Student> studentSet;
    ArrayList<Skill> skillSet;
    JSONArray jsonArray = null;
    ArrayList<HashMap<String,String>> mainList;

    public CustomGridAdapter(Context context, ArrayList<Student> studentset,ArrayList<Skill> skillset,ArrayList<HashMap<String,String>> mainList) {
        this.context = context;

        //converting object array to string array
//        items = new String[objects.length];
//        for(int i=0;i<objects.length;i++) {
//            items[i] = objects[i].toString();
//        }
        this.studentSet = studentset;
        this.skillSet = skillset;
        this.mainList = mainList;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_list_item_grid, null);
        }

        final TextView textView = (TextView) convertView.findViewById(R.id.grid_tv);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.grid_cb);

        final int stno=position%(studentSet.size()+1)-1,skno=position/(studentSet.size()+1);

        //Log.e("stejasvin.getView","getView "+stno+" "+skno);

        if(stno!=-1) {

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        String date = Utilities.getDate(Utilities.getCurrentTime());
                        mainList.get(stno).put(skillSet.get(skno).getLid()+"",date);
//                        mainList.get(position%(studentSet.size()+1)).get(position/studentSet.size()).setDate(date);
//                        mainList.get(position%(studentSet.size()+1)).get(position/studentSet.size()).setId(skillSet.get(position/studentSet.size()).getLid()+"");
                        checkBox.setText(date);
                    }else{

                        checkBox.setText("-");
                        mainList.get(stno).remove(skillSet.get(skno).getLid()+"");
                    }
                }
            });

            textView.setVisibility(View.GONE);
            checkBox.setVisibility(View.VISIBLE);
            String s = mainList.get(stno).get(skillSet.get(skno).getLid()+"");
            if(s == null) {
                Log.e("stejasvin.getView","s null    "+stno+" "+skno);
                checkBox.setText(" - ");
                checkBox.setChecked(false);
            }
            else {
                Log.e("stejasvin.getView","s != null "+stno+" "+skno+" "+s);
                checkBox.setChecked(true);
                checkBox.setText(s);
            }


//            if (items[position].equals("-")) {
//
//                int sid, skid;
//                textView.setVisibility(View.GONE);
//                checkBox.setVisibility(View.VISIBLE);
//                checkBox.setText("Date");
//
//            } else {
//                checkBox.setVisibility(View.VISIBLE);
//                textView.setVisibility(View.VISIBLE);
//                textView.setText(items[position]);
//            }
        }else{
            checkBox.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(skillSet.get(skno).getName());
            checkBox.setOnCheckedChangeListener(null);
        }



//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100,100);
//        LinearLayout llGrid = (LinearLayout)convertView.findViewById(R.id.grid_ll);
//        llGrid.setLayoutParams(layoutParams);


        return convertView;
    }

    @Override
    public int getCount() {
        return skillSet.size()*(studentSet.size()+1);
    }

    @Override
    public Object getItem(int position) {
        int stno=position%(studentSet.size()+1)-1,skno=position/(studentSet.size()+1);
        if(stno==-1){
            return new HashMap<String,String>();
        }else {
            return mainList.get(stno).get(skillSet.get(skno));
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}