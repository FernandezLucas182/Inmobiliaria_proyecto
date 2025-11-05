package com.example.inmob.ui.contratos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmob.R;
import com.example.inmob.model.Pago;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.PagoViewHolder> {

    private final List<Pago> listaPagos;
    private final Context context;

    public PagosAdapter(List<Pago> listaPagos, Context context) {
        this.listaPagos = listaPagos;
        this.context = context;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.pagos_card, parent, false);
        return new PagoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago p = listaPagos.get(position);
        holder.tvDetalle.setText(p.getDetalle());
        holder.tvFecha.setText("Fecha: " + formatoLocal(p.getFechaPago()));
        holder.tvMonto.setText("Monto: $" + p.getMonto());
        holder.tvEstado.setText(p.isEstado() ? "Pagado" : "Pendiente");
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public static class PagoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDetalle, tvFecha, tvMonto, tvEstado;
        private final CardView cardPago;

        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDetalle = itemView.findViewById(R.id.tvDetallePago);
            tvFecha = itemView.findViewById(R.id.tvFechaPago);
            tvMonto = itemView.findViewById(R.id.tvMontoPago);
            tvEstado = itemView.findViewById(R.id.tvEstadoPago);
            cardPago = itemView.findViewById(R.id.cardPago);
        }
    }
    private String formatoLocal(String fechaIso) {
        try {
            LocalDate fecha = LocalDate.parse(fechaIso);
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "AR"));
            return fecha.format(formato);
        } catch (Exception e) {
            return fechaIso;
        }
    }
}
