package com.example.inmob.ui.contratos;

import static com.example.inmob.request.ApiClient.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmob.R;
import com.example.inmob.model.Inmueble;

import java.util.List;

public class ContratosAdapter extends RecyclerView.Adapter<ContratosAdapter.ContratoViewHolder> {
    private Context context;
    private List<Inmueble> lista;

    public ContratosAdapter(List<Inmueble> lista, Context context) {
        this.lista = lista;
        this.context = context;
}

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contratos_card, parent, false);
        return new ContratoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoViewHolder holder, int position) {
        Inmueble inmueble = lista.get(position);

        holder.tvDireccion.setText(inmueble.getDireccion());
        holder.tvPrecio.setText(String.valueOf(inmueble.getValor()));
        Glide.with(context)
                .load(BASE_URL + inmueble.getImagen())
                .placeholder(R.drawable.img)
                .error(R.drawable.error)
                .into(holder.imgInmueble);

        // lleva al fragment detalle del contrato
        holder.cvContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idInmueble", inmueble.getIdInmueble());
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                        .navigate(R.id.detalleContratoFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ContratoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDireccion;
        private TextView tvPrecio;
        private ImageView imgInmueble;
        private CardView cvContrato;


        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            imgInmueble = itemView.findViewById(R.id.imgInmueble);
            cvContrato = itemView.findViewById(R.id.cvContrato);
        }



    }
}
