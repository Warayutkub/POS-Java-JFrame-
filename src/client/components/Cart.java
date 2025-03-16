package client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import backend.services.InventoryService;
import resources.Tools;

public class Cart extends JPanel {
    private ArrayList<String> storeProduct = new ArrayList<>();
    private String key = "0";
    private int width = 300;
    private int height = 600;
    private InnerCart modelCart = new InnerCart();
    private JPanel body = Body();
    private JPanel footer = Footer();

    public Cart() {
        CreateGui();
    }

    private void CreateGui() {
        setPreferredSize(new Dimension(width, height));
        setBorder(new LineBorder(Color.RED));
        setLayout(new BorderLayout());
        add(body, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }


    private JPanel Body() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        return area;
    }

    private JPanel Footer() {
        JPanel area = new JPanel(new GridLayout(5, 1));
        area.add(TotalPrice());
        area.add(TotalAmount());
        area.add(SubmitBTn());
        return area;
    }

    private JPanel TotalPrice() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel totalLabel = new JLabel("Total Price : ");
        JLabel totalPrice = new JLabel(modelCart.getTotalPrice());
        area.add(totalLabel);
        area.add(totalPrice);
        return area;
    }

    private JPanel TotalAmount() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel totalLabel = new JLabel("Total Amount");
        JLabel totalAmount = new JLabel(modelCart.getTotalAmount());
        area.add(totalLabel);
        area.add(totalAmount);
        return area;
    }

    private JButton SubmitBTn() {
        JButton Btn = new JButton("Submit Order");

        Btn.addActionListener(e -> {
            modelCart.WriteData();
            body.removeAll();
            this.key = "0";
            modelCart.resetData();
            storeProduct.removeAll(storeProduct);
            revalidate();
            repaint();
        });
        return Btn;
        
    }

    // For click in mainframe
    public void addProduct(ProductCard dataForCard) {
        ProductCardRight card = new ProductCardRight(dataForCard.getName(), dataForCard.getPrice(), this);
        String total = String.valueOf(Integer.parseInt(card.getAmount()) * Double.parseDouble(card.getPrice()));
        String cardData[] = new String[] { card.getType(), card.getName(), card.getPrice(), card.getAmount(), total,
                card.getID(), this.key };
        body.add(card);
        modelCart.addData(cardData);
        genKey();
        footer.removeAll();
        footer.add(TotalPrice());
        footer.add(TotalAmount());
        footer.add(SubmitBTn());
        footer.revalidate();
        footer.repaint();
    }

    public void deleteProduct(ProductCardRight card){
        body.remove(card);
        body.revalidate();
        body.repaint();
        storeProduct.remove(card.getID());
        modelCart.deleteData(card.getID());
    }

    public void setAmountProduct(String id, String amount) {
        modelCart.setAmountProductModel(id, amount);
    }

    private void genKey() {
        this.key = String.valueOf(Integer.parseInt(this.key) + 1);
    }

    public void addProductToStoreID(String id){
        this.storeProduct.add(id);
    }

    public Boolean CheckProduct(String id){
        return storeProduct.contains(id);
    }

    public void setFooter(){
        footer.removeAll();
        footer.add(TotalPrice());
        footer.add(TotalAmount());
        footer.add(SubmitBTn());
        footer.revalidate();
        footer.repaint();
    }



}

class InnerCart {
    private String TotalAmount = "0";
    private String TotalPrice = "0";
    private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(" HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();

    // type,ProductName,price,Amount,Total,id,key
    private ArrayList<String[]> dataProduct = new ArrayList<>();
    private ArrayList<String> dataOut = new ArrayList<>();

    // BillId,date,Time
    public InnerCart() {
        dataOut.add(new Tools().genNewId(new InventoryService().getAllSalesHistory()));
        dataOut.add(String.valueOf(now.format(formatterDate)));
        dataOut.add(String.valueOf(now.format(formatterTime)));
    }

    public void addData(String[] newData) {
        this.dataProduct.add(newData);
        this.TotalAmount = String.valueOf(Integer.parseInt(newData[3]) + Integer.parseInt(this.TotalAmount));
        this.TotalPrice = String.valueOf((Double.parseDouble(newData[2]) * Double.parseDouble(newData[3])) + Double.parseDouble(this.TotalPrice));
    }

    public void resetData() {
        dataProduct.removeAll(dataProduct);
    }

    public void deleteData(String id){
        String[] item = new String[0];
        for (int c = 0; c < dataProduct.size(); c++) {
            if (dataProduct.get(c)[5].equals(id)) {
                item = dataProduct.get(c);
                break;
            }
        }
        dataProduct.remove(item);
    }

    public void setAmountProductModel(String id, String amount) {
        String key = "";

        for (int c = 0; c < dataProduct.size(); c++) {
            if (dataProduct.get(c)[5].equals(id)) {
                key = dataProduct.get(c)[6];
                break;
            }
        }

        for (int c = 0; c < dataProduct.size(); c++) {
            if (dataProduct.get(c)[6].equals(key)) {
                this.TotalAmount = String.valueOf((Integer.parseInt(this.TotalAmount) - Integer.parseInt(dataProduct.get(c)[3])) + Integer.parseInt(amount));
                this.TotalPrice = String.valueOf((Double.parseDouble(this.TotalPrice) - Double.parseDouble(dataProduct.get(c)[4])) + Double.parseDouble(amount) * Double.parseDouble(dataProduct.get(c)[2]));
                dataProduct.get(c)[3] = amount;
                dataProduct.get(c)[4] = String
                        .valueOf(Double.parseDouble(amount) * Double.parseDouble(dataProduct.get(c)[2]));
            }
        }
    }

    // Write Data To File History(InMemoryStore.txt)
    // BillId,typeProduct,date,time,costumerName,productName,QTY,total(Bath)
    public void WriteData() {
        String[][] DataFormatted = new String[dataProduct.size()][7];
        for (int index = 0; index < DataFormatted.length; index++) {
            DataFormatted[index] = new String[] { dataOut.get(0), dataProduct.get(index)[0], dataOut.get(1),
                    dataOut.get(2), "Warayut", dataProduct.get(index)[1], dataProduct.get(index)[3],
                    dataProduct.get(index)[4] };
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/InMemoryStore.txt", true))) {
            for (String[] dataWrite : DataFormatted) {
                writer.write(dataWrite[0] + "," + dataWrite[1] + "," + dataWrite[2] + "," +
                        dataWrite[3] + "," + dataWrite[4] + "," + dataWrite[5] + "," + dataWrite[6]
                        + "," + dataWrite[7] + "\n");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Cart modelCart");
        }
    }

    public String getTotalAmount() {
        return this.TotalAmount;
    }

    public String getTotalPrice() {
        return this.TotalPrice;
    }


}