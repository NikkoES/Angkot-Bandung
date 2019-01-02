package id.co.kosankoding.angkotbandung.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.kosankoding.angkotbandung.R;
import id.co.kosankoding.angkotbandung.model.Angkot;

import static id.co.kosankoding.angkotbandung.utils.Constant.BASE_IMAGE_URL;

public class AngkotAdapter extends RecyclerView.Adapter<AngkotAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Angkot> listAngkot;
    private List<Angkot> listAngkotFiltered;

    public AngkotAdapter(Context context, List<Angkot> listAngkot) {
        this.context = context;
        this.listAngkot = listAngkot;
        this.listAngkotFiltered = listAngkot;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_angkot, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Angkot angkot = listAngkotFiltered.get(position);
        Glide.with(context).load(BASE_IMAGE_URL + angkot.getGambar()).placeholder(R.drawable.ic_armada).into(holder.imageAngkot);
        holder.txtTrayekAngkot.setText(angkot.getTrayek());
        holder.txtKodeAngkot.setText("0" + angkot.getKodeAngkot());
        holder.txtJumlahArmada.setText(angkot.getJumlahArmada());
        holder.txtJarak.setText(angkot.getJarak() + " km");
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listAngkotFiltered = listAngkot;
                } else {
                    List<Angkot> filteredList = new ArrayList<>();
                    for (Angkot row : listAngkot) {
                        if (row.getTrayek().toLowerCase().contains(charString.toLowerCase()) ||
                                ("0" + row.getKodeAngkot()).toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    listAngkotFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listAngkotFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listAngkotFiltered = (ArrayList<Angkot>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return listAngkotFiltered.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_angkot)
        ImageView imageAngkot;
        @BindView(R.id.txt_trayek_angkot)
        TextView txtTrayekAngkot;
        @BindView(R.id.txt_kode_angkot)
        TextView txtKodeAngkot;
        @BindView(R.id.txt_jumlah_armada)
        TextView txtJumlahArmada;
        @BindView(R.id.txt_jarak)
        TextView txtJarak;
        @BindView(R.id.cv_angkot)
        CardView cvAngkot;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
