package adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room_data_base_second.R;

import org.w3c.dom.Text;

import java.util.List;

import dataBase.ContactEntity;

public class ContactAdaptor extends RecyclerView.Adapter<ContactAdaptor.ContactAdaptorViewHolder> {
    List<ContactEntity> contactEntityList;
    ContactAdaptorClick contactAdaptorClick;

    public void setContactAdaptorClick(ContactAdaptorClick contactAdaptorClick) {
        this.contactAdaptorClick = contactAdaptorClick;
    }

    public ContactAdaptor(List<ContactEntity> contactEntityList) {
        this.contactEntityList = contactEntityList;
    }

    @NonNull
    @Override
    public ContactAdaptorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row_view_layout,parent,false);
        return new ContactAdaptorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdaptorViewHolder holder, int position) {
        holder.tvMail.setText(contactEntityList.get(position).getEmail());
        holder.tvName.setText(contactEntityList.get(position).getName());
        holder.tvId.setText(String.valueOf(contactEntityList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return contactEntityList.size();
    }

    public class ContactAdaptorViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView tvMail,tvName,tvId;
        public ContactAdaptorViewHolder(@NonNull View itemView) {
            super(itemView);
            cv=itemView.findViewById(R.id.cv);
            tvMail=itemView.findViewById(R.id.tvMail);
            tvName=itemView.findViewById(R.id.tvName);
            tvId=itemView.findViewById(R.id.tvId);
            itemView.setOnClickListener(v -> {
                if (contactAdaptorClick!=null){
                    contactAdaptorClick.contactClick(v,getAdapterPosition());
                }
            });

        }
    }
    public interface ContactAdaptorClick{
        void contactClick(View view,int index);
    }
}
