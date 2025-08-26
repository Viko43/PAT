def cambiar_moneda():
    
    denominaciones = [500, 200, 100, 50, 20, 10, 5, 2, 1]
    
    while True:

        try:
            cantidad = int(input())
            
            if cantidad == 0:
                break
            print(cantidad)
            for denominaciones in denominaciones:
                cantidad_billetes = cantidad // denominaciones

                if cantidad_billetes > 0:
                    print(f"{cantidad_billetes} de {denominaciones}")
                    cantidad %= denominaciones
        except ValueError:
            print("Numero invalido, ingresa otra cantidad")

if __name__ == "__main__":
    cambiar_moneda()

#TRABAJAMOS VICTOR MANUEL LANDEROS CANO Y MORENO RODRIGUEZ ROBERTO 