package gnu.cs.mamddadong;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> arrayList;
    private Context context;
    private String PostKey;

    public PostAdapter(ArrayList<Post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        PostViewHolder holder = new PostViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImage())
                .into(holder.pt_image);
        holder.pt_name.setText(arrayList.get(position).getName());
        holder.pt_location.setText(arrayList.get(position).getLocation());
        holder.pt_title.setText(arrayList.get(position).getTitle());

        holder.pt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ptKey = arrayList.get(position).getKey();
                String ptname = arrayList.get(position).getName();
                String pttitle = arrayList.get(position).getTitle();
                String ptlocation = arrayList.get(position).getLocation();
                String ptcontext = arrayList.get(position).getContent();
                String ptimage = arrayList.get(position).getImage();
                String ptprofile = arrayList.get(position).getProfile();
                String ptday = arrayList.get(position).getDay();
                String ptid = arrayList.get(position).getUser_id();
                Intent in = new Intent(context, LookupActivity.class);
                in.putExtra("ptKey", ptKey);
                in.putExtra("ptname", ptname);
                in.putExtra("pttitle", pttitle);
                in.putExtra("ptlocation", ptlocation);
                in.putExtra("ptcontext", ptcontext);
                in.putExtra("ptimage", ptimage);
                in.putExtra("ptprofile", ptprofile);
                in.putExtra("ptday", ptday);
                in.putExtra("ptid", ptid);

                context.startActivity(in);
                ((Activity)context).overridePendingTransition(0, 0);
            }
        });

        holder.pt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(arrayList.get(position).getTitle())
                        .setMessage("해당 게시글을 삭제하시겠습니까?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String ptid = arrayList.get(position).getUser_id();
                                String user_id = String.valueOf(KakaoLoginActivity.user_id);

                                if (ptid.equals(user_id)) {
                                    PostKey = arrayList.get(position).getKey();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Post").child("나눔");
                                    DatabaseReference myRef2 = database.getReference("Post").child("부탁");
                                    DatabaseReference myRef3 = database.getReference("Post").child("봉사");
                                    DatabaseReference myRef4 = database.getReference("MyPost").child(String.valueOf(KakaoLoginActivity.user_id));

                                    myRef.child(PostKey).removeValue();
                                    myRef2.child(PostKey).removeValue();
                                    myRef3.child(PostKey).removeValue();
                                    myRef4.child(PostKey).removeValue();
                                    notifyItemRemoved(position);
                                    Intent in = new Intent(context, CategoryActivity.class);
                                    context.startActivity(in);
                                    ((Activity)context).overridePendingTransition(0, 0);

                                } else {
                                    Toast.makeText(context.getApplicationContext(), "게시글 삭제 권한이 없습니다", Toast.LENGTH_LONG).show();
                                }
                                Toast.makeText(context.getApplicationContext(), "게시글이 삭제되었습니다", Toast.LENGTH_LONG).show();
                            }}).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView pt_image;
        TextView pt_name;
        TextView pt_location;
        TextView pt_title;
        Button pt_button;
        Button pt_remove;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pt_image = itemView.findViewById(R.id.pt_profile);
            this.pt_title = itemView.findViewById(R.id.pt_title);
            this.pt_location = itemView.findViewById(R.id.pt_location);
            this.pt_name = itemView.findViewById(R.id.pt_name);
            this.pt_button = itemView.findViewById(R.id.pt_Button);
            this.pt_remove = itemView.findViewById(R.id.remove_button);
        }
    }
}
