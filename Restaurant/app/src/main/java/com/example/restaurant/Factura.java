package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.Locale;

public class Factura extends AppCompatActivity {
    private EditText txtPedidos;
    private TextView tvidFactura, tvCliente, tvFechaF, tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_factura);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        txtPedidos = (EditText) findViewById(R.id.txtPedido);
        tvidFactura = (TextView) findViewById(R.id.tvidFact);
        tvFechaF = (TextView) findViewById(R.id.tvFechaF);
        tvCliente = (TextView) findViewById(R.id.tvCliente);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        // Cargar datos del archivo
        cargarDatosDesdeArchivo();
    }

    private void cargarDatosDesdeArchivo() {
        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("Ordenes.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea;
            StringBuilder todoElContenido = new StringBuilder();
            String fecha = "";
            String factura = "";
            String cliente = "";
            int total = 0;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Fecha:")) {
                    fecha = linea.substring(linea.indexOf(":") + 1).trim();
                } else if (linea.startsWith("Factura:")) {
                    factura = linea.substring(linea.indexOf(":") + 1).trim();
                } else if (linea.startsWith("Nombre Cliente:")) {
                    cliente = linea.substring(linea.indexOf(":") + 1).trim();
                } else if (linea.startsWith("Producto:") || linea.startsWith("Precio:")) {
                    todoElContenido.append(linea).append("\n");
                    if (linea.startsWith("Precio:")) {
                        String precioStr = linea.substring(linea.indexOf("$") + 1).trim().replace(".", "");
                        try {
                            int precio = Integer.parseInt(precioStr);
                            total += precio;
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
            br.close();
            archivo.close();

            // Mostrar la información en los campos correspondientes
            tvFechaF.setText(fecha);
            tvidFactura.setText(factura);
            tvCliente.setText(cliente);
            txtPedidos.setText(todoElContenido.toString());

            // Formatear y mostrar el total
            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CL"));
            String totalFormateado = formatoMoneda.format(total);
            tvTotal.setText(totalFormateado);

        } catch (IOException e) {
            Toast.makeText(this, "Error al leer el archivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnVolver4 (View v){
        // Borrar el contenido del archivo
        borrarContenidoArchivo();

        // Volver a la actividad principal
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void borrarContenidoArchivo() {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("Ordenes.txt", MODE_PRIVATE));
            archivo.write(""); // Escribe una cadena vacía, efectivamente borrando el contenido
            archivo.close();
            Toast.makeText(this, "Adios, Vuelve Pronto " , Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error al borrar el archivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnSushi3 (View v){
        Toast.makeText(this, "Esta es la factura electrónica", Toast.LENGTH_SHORT).show();
    }

    public void btnEliminar (View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }
    public void btnModificar (View v){
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }

}