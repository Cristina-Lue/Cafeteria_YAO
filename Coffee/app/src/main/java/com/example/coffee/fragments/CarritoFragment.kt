package com.example.coffee.fragments

import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.R
import com.example.coffee.adapters.CarritoAdapter
import com.example.coffee.services.CarritoService
import java.io.File
import java.io.FileOutputStream

class CarritoFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var tvTotal: TextView? = null
    private var tvVacio: TextView? = null
    private var btnProcederPago: Button? = null
    private var containerPago: View? = null
    private var adapter: CarritoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.recyclerViewCarrito)
        tvTotal = view.findViewById(R.id.tvTotalCarrito)
        tvVacio = view.findViewById(R.id.tvCarritoVacio)
        btnProcederPago = view.findViewById(R.id.btnProcederPago)
        containerPago = view.findViewById(R.id.containerPago)

        recyclerView?.layoutManager = LinearLayoutManager(requireContext())



        addFallbackFinalizeButtonIfNeeded(view)


        CarritoService.agregarListener {
            actualizarUI()
        }


        btnProcederPago?.setOnClickListener {
            onFinalizeClicked()
        }


        actualizarUI()
    }

    private fun addFallbackFinalizeButtonIfNeeded(root: View) {

        if (btnProcederPago != null) return


        val parent = root as? ViewGroup ?: return


        val fallbackContainer = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(24, 12, 24, 24)
            gravity = Gravity.CENTER
        }

        val fallbackButton = Button(requireContext()).apply {
            text = "Finalizar compra"
            setAllCaps(false)
            textSize = 16f


            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                onFinalizeClicked()
            }
        }


        fallbackContainer.addView(fallbackButton)


        parent.addView(fallbackContainer)


        btnProcederPago = fallbackButton
        containerPago = fallbackContainer
    }

    private fun actualizarUI() {
        val carrito = CarritoService.obtenerCarrito()


        if (carrito.isEmpty()) {
            tvVacio?.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
            containerPago?.visibility = View.GONE
        } else {
            tvVacio?.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            containerPago?.visibility = View.VISIBLE


            adapter = CarritoAdapter(
                carrito,
                onCantidadChange = { id, nuevaCantidad ->
                    CarritoService.actualizarCantidad(id, nuevaCantidad)
                },
                onEliminarClick = { id ->
                    CarritoService.eliminarDelCarrito(id)
                }
            )
            recyclerView?.adapter = adapter
        }


        val total = CarritoService.calcularTotal()
        if (tvTotal != null) {
            tvTotal?.text = "Total: $${String.format("%.2f", total)}"
        } else {

            btnProcederPago?.text = if (total > 0.0) {
                "Finalizar compra — Total: $${String.format("%.2f", total)}"
            } else {
                "Finalizar compra"
            }
        }
    }

    private fun onFinalizeClicked() {
        val carrito = CarritoService.obtenerCarrito()
        if (carrito.isEmpty()) {
            Toast.makeText(requireContext(), "Tu carrito está vacío", Toast.LENGTH_SHORT).show()
            return
        }
        generarPDF()


        CarritoService.limpiarCarrito()
        actualizarUI()
        Toast.makeText(requireContext(), "Compra finalizada. Carrito vaciado.", Toast.LENGTH_SHORT).show()


    }


    private fun generarPDF() {
        val carrito = CarritoService.obtenerCarrito()
        if (carrito.isEmpty()) {
            Toast.makeText(requireContext(), "Tu carrito está vacío", Toast.LENGTH_SHORT).show()
            return
        }

        val pdf = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = pdf.startPage(pageInfo)
        val canvas = page.canvas
        val paint = android.graphics.Paint().apply { textSize = 12f }

        var y = 40f
        canvas.drawText("Recibo de compra", 90f, y, paint)
        y += 20

        carrito.forEach {
            canvas.drawText("${it.producto.nombre} x${it.cantidad}", 10f, y, paint)
            y += 15
            canvas.drawText("  $${String.format("%.2f", it.producto.precio * it.cantidad)}", 10f, y, paint)
            y += 20
        }

        y += 10
        canvas.drawText("Total: $${String.format("%.2f", CarritoService.calcularTotal())}", 10f, y, paint)
        y += 30
        canvas.drawText("Gracias por tu compra!", 80f, y, paint)

        pdf.finishPage(page)

        val file = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "recibo.pdf")
        try {
            pdf.writeTo(FileOutputStream(file))
            pdf.close()

            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            startActivity(intent)

            Toast.makeText(requireContext(), "PDF generado correctamente", Toast.LENGTH_SHORT).show()

            CarritoService.limpiarCarrito()
            actualizarUI()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error al generar PDF: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
