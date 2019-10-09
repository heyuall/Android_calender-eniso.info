package sun.bob.mcalendarviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    public Context mconctext;
    public ArrayList<NewEvent> mlist;

    public EventAdapter(Context context, ArrayList<NewEvent> list){
        this.mconctext=context;
        this.mlist=list;
    }
    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mconctext).inflate(R.layout.event_structure, parent, false);
        EventAdapter.ViewHolder viewHolder= new EventAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        NewEvent event = mlist.get(position);
        TextView s,e,t;
        s=holder.start;
        e=holder.end;
        t=holder.titre;

        s.setText(event.getStartdate());
        e.setText(event.getEnddate());
        t.setText(event.getTitle());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView start,end,titre;

        public ViewHolder(View itemView) {
            super(itemView);

            start= itemView.findViewById(R.id.de);
            end=itemView.findViewById(R.id.a);
            titre=itemView.findViewById(R.id.titre);
        }
    }
}
