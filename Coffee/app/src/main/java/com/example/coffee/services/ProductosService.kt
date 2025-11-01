package com.example.coffee.services

import com.example.coffee.models.Producto
import com.example.coffee.R
object ProductosService {

    fun obtenerCafes(): List<Producto> {
        return listOf(
            Producto(
                id = "1",
                nombre = "Frapuccino",
                precio = 4.50,
                descripcion = "Bebida refrescante a base de café",
                categoria = "cafe",
                imagenRes = R.drawable.frapuccino
            ),
            Producto(
                id = "2",
                nombre = "Capuccino",
                precio = 2.25,
                descripcion = "Café espresso con leche espumosa",
                categoria = "cafe",
                imagenRes = R.drawable.capuccino
            ),
            Producto(
                id = "3",
                nombre = "Espresso",
                precio = 1.99,
                descripcion = "Café concentrado y aromático",
                categoria = "cafe",
                imagenRes = R.drawable.espresso
            ),
            Producto(
                id = "4",
                nombre = "Matcha",
                precio = 4.75,
                descripcion = "Té verde matcha latte",
                categoria = "cafe",
                imagenRes = R.drawable.matcha
            )
        )
    }

    fun obtenerPostres(): List<Producto> {
        return listOf(
            Producto(
                id = "5",
                nombre = "Tartaleta",
                precio = 3.50,
                descripcion = "Tarta individual con frutas",
                categoria = "postre",
                imagenRes = R.drawable.tartaleta
            ),
            Producto(
                id = "6",
                nombre = "Alfajores",
                precio = 1.00,
                descripcion = "Dulces rellenos de dulce de leche",
                categoria = "postre",
                imagenRes = R.drawable.alfajor
            ),
            Producto(
                id = "7",
                nombre = "Cheescake",
                precio = 4.75,
                descripcion = "Tarta de queso cremosa",
                categoria = "postre",
                imagenRes = R.drawable.cheescake
            ),
            Producto(
                id = "8",
                nombre = "Tiramisu",
                precio = 5.25,
                descripcion = "Postre italiano con café",
                categoria = "postre",
                imagenRes = R.drawable.tiramisu
            )
        )
    }

    fun obtenerPlatillos(): List<Producto> {
        return listOf(
            Producto(
                id = "9",
                nombre = "Sandwich",
                precio = 1.25,
                descripcion = "Sándwich fresco con ingredientes selectos",
                categoria = "platillo",
                imagenRes = R.drawable.sandwich
            ),
            Producto(
                id = "10",
                nombre = "Papas fritas",
                precio = 1.50,
                descripcion = "Papas crujientes con sal",
                categoria = "platillo",
                imagenRes = R.drawable.papas
            ),
            Producto(
                id = "11",
                nombre = "Croissant",
                precio = 3.00,
                descripcion = "Croissant de mantequilla horneado",
                categoria = "platillo",
                imagenRes = R.drawable.croassant
            ),
            Producto(
                id = "12",
                nombre = "Hamburguesa",
                precio = 2.75,
                descripcion = "Hamburguesa con carne y vegetales",
                categoria = "platillo",
                imagenRes = R.drawable.hamburguesa
            )
        )
    }
}