package gnu.cs.mamddadong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> arrayList;
    private Context context;

    public UserAdapter(ArrayList<User> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        UserViewHolder holder = new UserViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);

        holder.tv_userName.setText(arrayList.get(position).getUserName());

        holder.ct_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stFriendId = arrayList.get(position).getKey();
                String stprofile = arrayList.get(position).getProfile();
                String stName = arrayList.get(position).getUserName();
                Intent in = new Intent(context, ChattingActivity.class);
                in.putExtra("name", stName);
                in.putExtra("profile", stprofile);
                in.putExtra("friendid", stFriendId);
                context.startActivity(in);
                ((Activity)context).overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_userName;
        Button ct_button;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_userName = itemView.findViewById(R.id.tv_nickname);
            this.ct_button = itemView.findViewById(R.id.chat_button);
        }
    }
}
