package com.example.restaurant;

import android.app.Activity;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Menu extends AppCompatActivity {
    private TextView tvFechaActual, tvFacturacion;
    private EditText txtNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Mantener el formato original de las asignaciones
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        tvFechaActual = (TextView) findViewById(R.id.tvfecha);
        tvFacturacion = (TextView) findViewById(R.id.tvFactura);

        //Fechas
        Date fechaHoy = new Date();
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        SimpleDateFormat anno = new SimpleDateFormat("yyyy");

        String diaString = dia.format(fechaHoy);
        String mesString = mes.format(fechaHoy);
        String annoString = anno.format(fechaHoy);

        // Generamos un número aleatorio de 3 dígitos
        Random random = new Random();
        int numeroAleatorio = random.nextInt(900) + 100;

        //Fecha
        String fechaActual = diaString + "/" + mesString + "/" + annoString;
        //Facturación
        String fechactualFac = annoString+mesString+diaString+numeroAleatorio;
        tvFechaActual.setText("Fecha Factura: " + fechaActual);
        tvFacturacion.setText("Factura No: " + fechactualFac);

        //Verificar si existe el archivo
        String archivos[] = fileList();
        if (ArchivoExistente(archivos, "Ordenes.txt")) {
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("Ordenes.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                while (linea != null) {
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
            } catch (IOException e) {
                Toast.makeText(this, "Error al leer el archivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean ArchivoExistente(String Archivos[], String NombreArchivo) {
        for (int i = 0; i < Archivos.length; i++)
            if (NombreArchivo.equals(Archivos[i]))
                return true;
        return false;
    }

    public void btnFacturacion(View v) {
        String nombreCliente = txtNombre.getText().toString().trim();

        if (nombreCliente.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el nombre del cliente", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!hayProductosEnOrden()) {
            Toast.makeText(this, "Por favor, agregue al menos un producto a la orden", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("Ordenes.txt", Activity.MODE_APPEND));
            archivo.write("========== NUEVA ORDEN ==========\n");
            archivo.write("Fecha: " + tvFechaActual.getText().toString() + "\n");
            archivo.write("Factura: " + tvFacturacion.getText().toString() + "\n");
            archivo.write("Nombre Cliente: " + nombreCliente + "\n");
            archivo.write("---------- PRODUCTOS ----------\n");
            archivo.flush();
            archivo.close();
            Toast.makeText(this, "Orden guardada exitosamente", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, Factura.class);
            startActivity(i);
        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar la orden: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean hayProductosEnOrden() {
        try {
            InputStreamReader archivo = new InputStreamReader(openFileInput("Ordenes.txt"));
            BufferedReader br = new BufferedReader(archivo);
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("Producto:")) {
                    br.close();
                    archivo.close();
                    return true;
                }
            }
            br.close();
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void btnVolver4(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void btnSushi2(View v) {
        Toast.makeText(this, "Este es el Menú para poder Ordenar", Toast.LENGTH_SHORT).show();
    }

    public void btnEdaname(View v){
        agregarProducto("Edamame", "7.000");
    }

    public void btnGyaza(View v){
        agregarProducto("Gyaza", "6.000");
    }

    public void btnSpring(View v){
        agregarProducto("Spring Rolls", "7.000");
    }

    public void btnCoquetas(View v){
        agregarProducto("Croquetas de Salmon", "8.000");
    }

    public void btnSopa(View v){
        agregarProducto("Sopa de Miso", "6.000");
    }

    public void btnSmoked(View v){
        agregarProducto("Smoked salmon", "40.000");
    }

    public void btnMaki(View v){
        agregarProducto("Maki Roll", "40.000");
    }

    public void btnClasicos(View v){
        agregarProducto("Rollos Clasicos", "40.000");
    }

    public void btnVeggie(View v){
        agregarProducto("Veggie Sushi", "40.000");
    }

    public void btnPeanut(View v){
        agregarProducto("Peanut Avocado", "40.000");
    }

    public void btnAgua(View v){
        agregarProducto("Agua", "3.000");
    }

    public void btnGaseosa(View v){
        agregarProducto("Gaseosa", "5.000");
    }

    public void btnCoco(View v){
        agregarProducto("Limonada de Coco", "6.000");
    }

    public void btnCereza(View v){
        agregarProducto("Limonada de Cereza", "6.000");
    }

    public void btnJugo(View v){
        agregarProducto("Jugo Natural", "6.000");
    }

    public void btnTiramisu(View v){
        agregarProducto("Tiramisu", "8.000");
    }

    public void btnMacarons(View v){
        agregarProducto("Macarons", "12.000");
    }

    public void btnLulada(View v){
        agregarProducto("Lulada", "8.000");
    }

    public void btnCafe(View v){
        agregarProducto("Postre de Cafe", "7.000");
    }

    public void btnMiloja(View v){
        agregarProducto("Milhoja", "9.000");
    }

    private void agregarProducto(String nombre, String precio) {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("Ordenes.txt", Activity.MODE_APPEND));
            archivo.write("Producto: " + nombre + "\n");
            archivo.write("Precio: $" + precio + "\n");
            archivo.write("----------------------------------------\n");
            archivo.flush();
            archivo.close();
            Toast.makeText(this, nombre + " agregado a la orden", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar el producto: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}