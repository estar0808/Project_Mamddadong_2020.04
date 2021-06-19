package gnu.cs.mamddadong;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private ArrayList<Chat> arrayList;
    private String stMyemail = "";

    public ChatAdapter(ArrayList<Chat> arrayList, String stEmail) {
        this.arrayList = arrayList;
        this.stMyemail = stEmail;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_left_item, parent, false);
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_right_item, parent, false);
        }
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).userName.equals(stMyemail)) {
            return 1;
        } else{
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.mTextView);
        }
    }
}
