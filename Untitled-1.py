def calcular_cambio(monto, inventario):
    
    denominaciones = sorted(inventario.keys(), reverse=True)
    cambio = {}
    restante = monto

    for d in denominaciones:
        disponibles = inventario[d]
        requeridos = restante // d
        usar = min(disponibles, requeridos)

        if usar > 0:
            cambio[d] = usar
            restante -= usar * d

    if restante == 0:
        return cambio
    else:
        return None
    
inventario = {
    500: 10,
    200: 12,
    100: 15,
    50: 20,
    20: 15,
    10: 30,
    5: 20,
    2: 30,
    1: 90
}

def mostrar_inventario(inv):
    print("\nInventario actual de billetes/monedas:")
    for denom in sorted(inv.keys(), reverse=True):
        print(f"{denom}: {inv[denom]} disponibles")

while True:
    mostrar_inventario(inventario)

    try:
        entrada = input("\nIngresa la cantidad a cambiar : ")
        monto = int(entrada)

        if monto == 0:
            print()
            break

      
        resultado = calcular_cambio(monto, inventario.copy())

        if resultado:
            print(f"\nCambio para {monto}€:")
            for d in sorted(resultado.keys(), reverse=True):
                print(f"{resultado[d]} de {d}")

            for d, cantidad in resultado.items():
                inventario[d] -= cantidad
        else:
            print(f"\nNo es posible dar el cambio exacto para {monto}€ con las denominaciones disponibles.")

    except ValueError:
        print("Por favor, ingresa un número entero válido.")