package org.mariqzw.adapter.primary.console;

import org.mariqzw.domain.model.Order;
import org.mariqzw.domain.model.OrderItem;
import org.mariqzw.domain.model.ProductDemandForecast;
import org.mariqzw.domain.port.primary.SupplierOrderUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleOrderManager {
    private final SupplierOrderUseCase useCase;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleOrderManager(SupplierOrderUseCase useCase) {
        this.useCase = useCase;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Меню ===");
            System.out.println("1. Создать заказ ");
            System.out.println("2. Отправить заказ ");
            System.out.println("3. Получить подтверждение ");
            System.out.println("4. Узнать статус заказа ");
            System.out.println("5. Подтвердить получение ");
            System.out.println("6. Вернуть заказ ");
            System.out.println("0. Выход ");
            System.out.print("Выберите действие> ");

            switch (scanner.nextLine()) {
                case "1": create(); break;
                case "2": send(); break;
                case "3": confirm(); break;
                case "4": track(); break;
                case "5": accept(); break;
                case "6": returnOrder(); break;
                case "0": return;
                default: System.out.println("Неверный ввод. Введите число от 0 до 6");
            }
        }
    }

    private void create() {
        System.out.print("Введите количество позиций: ");
        int number = Integer.parseInt(scanner.nextLine());
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            System.out.print("ID позиции: "); String pid = scanner.nextLine();
            System.out.print("Количество: "); int q = Integer.parseInt(scanner.nextLine());
            items.add(new OrderItem(pid, q));
        }
        ProductDemandForecast f = new ProductDemandForecast(items);
        Order o = useCase.createOrder(f);
        System.out.println(">>> Заказ создан : " + o.getId());
    }

    private UUID readId() {
        System.out.print("ID заказа: ");
        return UUID.fromString(scanner.nextLine());
    }

    private void send() {
        useCase.sendOrder(readId());
        System.out.println(">>> Отправлен");
    }

    private void confirm() {
        useCase.receiveConfirmation(readId());
        System.out.println(">>> Подтвержден");
    }

    private void track() {
        System.out.println("Статус: " + useCase.trackStatus(readId()));
    }

    private void accept() {
        useCase.acceptDelivery(readId());
        System.out.println(">>> Получен & контроль качества пройден ");
    }

    private void returnOrder() {
        useCase.processReturn(readId());
        System.out.println(">>> Вовзращен");
    }
}
