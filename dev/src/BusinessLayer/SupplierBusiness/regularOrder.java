package BusinessLayer.SupplierBusiness;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class regularOrder extends Order{

    public regularOrder(int orderId , int branchId){
        super(orderId , LocalDate.now().plusDays(7), branchId);
    }

    public void updateDeliverTime(LocalDate deliverTime) throws Exception {
        // check if the date is at least one day before.
        long daysDiffrence = ChronoUnit.DAYS.between(LocalDate.now(), deliverTime);
        if(daysDiffrence >= 1) {
            dalOrder.updateDeliverTime(deliverTime);
        }
        else throw new Exception("deliver time must be at least one day after current time");
    }

    public void updateTotalAmount(double totalAmount){
        dalOrder.updateTotalAmount(totalAmount);
    }

    public void addItemToOrder(Item item , int amount) throws Exception {
        if(amount < 1) throw new Exception("amount must be at least 1");
        if (items.get(item) != null) {
            items.put(item, items.get(item) + amount);
            dalOrder.addItemToOrder(item.getItemId(), items.get(item)+ amount);
        }
        else items.put(item, amount);
        dalOrder.addItemToOrder(item.getItemId(), amount);
        updateTotalAmount(item , amount);
    }

    private void updateTotalAmount(Item item , int amount) throws Exception {
        QuantityDocument qd = item.getQuantityDocument();
        if (qd == null) throw new Exception("quantity document does not exist.");
        updateTotalAmount(dalOrder.getTotalAmount()+item.getPrice() * amount);
        if (qd.getMinimalAmount() <= items.get(item)) {
            double discount = qd.getDiscount() / 100.0;
            updateTotalAmount(dalOrder.getTotalAmount()-item.getPrice() * discount * amount);
        }
    }

    public void removeItemFromRegularOrder(int itemId) throws Exception {
        for(Item item : items.keySet()) {
            if (item.getItemId() == itemId) {
                items.remove(item);
                dalOrder.removeItemFromOrder(itemId);
                break;
            }
        }
    }

    public void removeAmountItemFromRegularOrder(int itemId, int amount) throws Exception { ////I Think it need to be modified
        for(Item item : items.keySet()){
            if(item.getItemId() == itemId){
                int newAmount = items.get(item) - amount;
                if(newAmount < 0) throw new Exception("amount must be less then the amount of the item in the order");
                if(newAmount == 0) items.remove(item);
                else items.put(item , items.get(item) - amount);
                break;
            }
        }
    }
}
