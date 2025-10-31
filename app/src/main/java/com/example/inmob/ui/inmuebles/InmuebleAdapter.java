package com.example.inmob.ui.inmuebles;

import static com.example.inmob.request.ApiClient.BASE_URL;

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

        // 1. FORMATEO DE MONEDA PROFESIONAL
        // Esto mostrará el precio con separadores de miles y símbolo de moneda.
        // Ejemplo: $ 150,000.00
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
        holder.tvPrecio.setText(formatoMoneda.format(i.getValor()));
        //holder.imgInmueble.setImageResource(R.drawable.img);

        // 2. CARGA DE IMAGEN MEJORADA CON GLIDE
        Glide.with(context)
                .load(BASE_URL + i.getImagen())
                .placeholder(R.drawable.img) // Imagen de carga
                .error(R.drawable.error)       // Imagen en caso de error
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Mejora el rendimiento guardando en caché
                .into(holder.imgInmueble);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setInmuebles(List<Inmueble> objects) {
    }

    public class InmuebleViewHolder extends RecyclerView.ViewHolder {
        private CardView idCV;
        private TextView tvDireccion, tvTipo, tvPrecio;
        private ImageView imgInmueble;

        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            imgInmueble = itemView.findViewById(R.id.imgInmueble);
            //idCV = itemView.findViewById(R.id.id_CardView);

            // Opcional:  agregar un OnClickListener a toda la tarjeta aquí
            // itemView.setOnClickListener(v -> { ... });
        }
    }
}
