package com.example.inmob.ui.inmuebles;

import static com.example.inmob.request.ApiClient.BASE_URL;

import android.os.Bundle;
import androidx.navigation.Navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmob.R;
import com.example.inmob.model.Inmueble;

import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

import androidx.core.content.ContextCompat;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {

    private List<Inmueble> lista;
    private Context context;


    public InmuebleAdapter(List<Inmueble> lista, Context context) {
        this.lista = lista;
        this.context = context;

    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inmueble_card, parent, false);
        return new InmuebleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {

        Inmueble i = lista.get(position);

        holder.tvDireccion.setText(i.getDireccion());
        holder.tvTipo.setText(i.getTipo());


        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        holder.tvPrecio.setText(formatoMoneda.format(i.getValor()));


        Glide.with(context)
                .load(BASE_URL + i.getImagen())
                .placeholder(R.drawable.img)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgInmueble);


        if (i.getDisponible()) {
            holder.tvDisponible.setText("Disponible");
            holder.tvDisponible.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
        } else {
            holder.tvDisponible.setText("No Disponible");
            holder.tvDisponible.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        }


        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", i);


            Navigation.findNavController(v).navigate(R.id.action_nav_inmuebles_to_detalleInmuebleFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
        //return lista.size();
    }


    public void setInmuebles(List<Inmueble> inmuebles) {
        this.lista = inmuebles;
        notifyDataSetChanged();
    }


    public class InmuebleViewHolder extends RecyclerView.ViewHolder {
        //private CardView idCV;
        private TextView tvDireccion, tvTipo, tvPrecio, tvDisponible;
        private ImageView imgInmueble;

        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            imgInmueble = itemView.findViewById(R.id.imgInmueble);
            tvDisponible = itemView.findViewById(R.id.tvDisponible);
            //idCV = itemView.findViewById(R.id.id_CardView);


        }
    }
}
