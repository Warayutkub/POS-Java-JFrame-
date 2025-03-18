package client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import backend.services.AuthService;
import backend.services.InventoryService;
import client.MainFrame;
import resources.SetPreferences;
import resources.Tools;

public class Cart extends JPanel {
    private String[] userData = new String[] { "null", "-", "null", "null", "null" };
    private ArrayList<String> storeProduct = new ArrayList<>();
    private String key = "0";
    private String phone;
    private boolean statusNewUser = false;
    private int width = 300;
    private int height = 600;
    private InnerCart modelCart = new InnerCart();
    private JPanel body = Body();
    private JPanel footer = Footer();
    private MainFrame mainFrame;
    private NewUserPopup newUserPopup;

    public Cart(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        CreateGui();
    }

    public Cart() {
        CreateGui();
    }

    private void CreateGui() {
        setPreferredSize(new Dimension(width, height));
        setBorder(new LineBorder(Color.RED));
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(body, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel Body() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        area.setBackground(Color.WHITE);
        return area;
    }

    private JPanel Footer() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        area.setPreferredSize(new Dimension(this.width, this.height / 3));
        area.setBorder(new LineBorder(Color.black));
        area.setBackground(Color.WHITE);
        area.add(CostumerArea());
        area.add(TotalPrice());
        area.add(TotalAmount());
        area.add(SubmitBTn());
        return area;
    }

    private JPanel CostumerArea() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField display = new JTextField("User data");
        JButton oldUser = new JButton("Old user");
        JButton newUser = new JButton("New user");

        area.setPreferredSize(new Dimension(this.width, 80));
        area.setBackground(Color.WHITE);
        display.setPreferredSize(new Dimension(this.width - 20, 30));
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setBorder(new LineBorder(Color.BLACK, 1));
        display.setHorizontalAlignment(JTextField.CENTER);

        area.add(display);
        area.add(oldUser);
        area.add(newUser);
        oldUser.addActionListener(e -> {
            while (true) {
                String input = JOptionPane.showInputDialog(mainFrame, "Enter User Phone", "Input user data", 1);
                if (input == null) {
                    break;
                }
                if (new AuthService().CheckDataUser(input, "user")) {
                    this.phone = input;
                    this.userData = new AuthService().getDataUser(this.phone);
                    display.setText(this.userData[1]);
                    display.setFont(new SetPreferences().getFont(14));
                    modelCart.setCNameProductModel(userData[1]);
                    break;
                } else {
                    int response = JOptionPane.showConfirmDialog(mainFrame, "User not found. Try again?", "Error",
                    JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.NO_OPTION) {
                        break;
                    }
                }
            }
        });
        
        newUser.addActionListener(e -> {
            
            newUserPopup = new NewUserPopup(mainFrame, this);
            newUserPopup.setVisible(true);
            newUserPopup.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    if (statusNewUser) {
                        userData = new AuthService().getDataUser(newUserPopup.getName());
                        display.setText(userData[1]);
                        display.setFont(new SetPreferences().getFont(14));
                        modelCart.setCNameProductModel(userData[1]);
                    }
                }
            });
        });

        return area;
    };

    private JPanel TotalPrice() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel totalLabel = new JLabel("Total Price : ");
        JLabel totalPrice = new JLabel(modelCart.getTotalPrice() + "$");
        area.setPreferredSize(new Dimension(this.width / 2, 30));
        area.setBackground(Color.WHITE);
        area.add(totalLabel);
        area.add(totalPrice);
        return area;
    }

    private JPanel TotalAmount() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel totalLabel = new JLabel("Total Amount : ");
        JLabel totalAmount = new JLabel(modelCart.getTotalAmount());
        area.setPreferredSize(new Dimension(this.width / 2 - 10, 30));
        area.setBackground(Color.WHITE);
        area.add(totalLabel);
        area.add(totalAmount);
        return area;
    }

    private JButton SubmitBTn() {
        JButton Btn = new JButton("Submit Order");
        Btn.setPreferredSize(new Dimension(this.width - 25, 30));
        Btn.setBackground(Color.green);
        Btn.addActionListener(e -> {
            modelCart.WriteData();
            body.removeAll();
            this.key = "0";
            modelCart.resetData();
            this.userData[1] = "null";
            storeProduct.removeAll(storeProduct);
            setFooter();
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
                card.getID(), this.key, userData[1] };
        body.add(card);
        modelCart.addData(cardData);
        genKey();
        setFooter();
        footer.revalidate();
        footer.repaint();
    }

    public void deleteProduct(ProductCardRight card) {
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

    public void addProductToStoreID(String id) {
        this.storeProduct.add(id);
    }

    public Boolean CheckProduct(String id) {
        return storeProduct.contains(id);
    }

    public ArrayList<String> getStoreProduct() {
        return this.storeProduct;
    }

    public void setTimeCart() {
        modelCart.setTime();
    }

    public void setFooter() {
        footer.removeAll();
        footer.add(CostumerArea());
        footer.add(TotalPrice());
        footer.add(TotalAmount());
        footer.add(SubmitBTn());
        footer.revalidate();
        footer.repaint();
    }

    public void SendStatusOfRegister(Boolean status) {
        this.statusNewUser = status;
    }

}

class InnerCart {
    private String TotalAmount = "0";
    private String TotalPrice = "0.0";

    // type,ProductName,price,Amount,Total,id,key
    private ArrayList<String[]> dataProduct = new ArrayList<>();
    private ArrayList<String> dataOut = new ArrayList<>();
    private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(" HH:mm:ss");

    // BillId,date,Time
    public InnerCart() {
        LocalDateTime now = LocalDateTime.now();
        dataOut.add(new Tools().genNewId(new InventoryService().getAllSalesHistory()));
        dataOut.add(String.valueOf(now.format(formatterDate)));
        dataOut.add(String.valueOf(now.format(formatterTime)));
    }

    public void addData(String[] newData) {
        this.dataProduct.add(newData);
        this.TotalAmount = String.valueOf(Integer.parseInt(newData[3]) + Integer.parseInt(this.TotalAmount));
        this.TotalPrice = String.valueOf((Double.parseDouble(newData[2]) * Double.parseDouble(newData[3]))
                + Double.parseDouble(this.TotalPrice));
    }

    public void resetData() {
        dataProduct.removeAll(dataProduct);
        this.TotalAmount = "0";
        this.TotalPrice = "0.0";
    }

    public void setTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Hey Time");
        dataOut.clear();
        dataOut.add(new Tools().genNewId(new InventoryService().getAllSalesHistory()));
        dataOut.add(String.valueOf(now.format(formatterDate)));
        dataOut.add(String.valueOf(now.format(formatterTime)));
    }

    public void deleteData(String id) {
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
                this.TotalAmount = String
                        .valueOf((Integer.parseInt(this.TotalAmount) - Integer.parseInt(dataProduct.get(c)[3]))
                                + Integer.parseInt(amount));
                this.TotalPrice = String
                        .valueOf((Double.parseDouble(this.TotalPrice) - Double.parseDouble(dataProduct.get(c)[4]))
                                + Double.parseDouble(amount) * Double.parseDouble(dataProduct.get(c)[2]));
                dataProduct.get(c)[3] = amount;
                dataProduct.get(c)[4] = String
                        .valueOf(Double.parseDouble(amount) * Double.parseDouble(dataProduct.get(c)[2]));
            }
        }
    }

    public void setCNameProductModel(String name) {
        for (int c = 0; c < dataProduct.size(); c++) {
            dataProduct.get(c)[7] = name;
        }
    }

    // Write Data To File History(InMemoryStore.txt)
    // BillId,typeProduct,date,time,costumerName,productName,QTY,total(Bath)
    public void WriteData() {
        String[][] DataFormatted = new String[dataProduct.size()][8];
        for (int index = 0; index < DataFormatted.length; index++) {
            System.out.println("Test ro");
            DataFormatted[index] = new String[] { dataOut.get(0), dataProduct.get(index)[0], dataOut.get(1),
                    dataOut.get(2), dataProduct.get(index)[7], dataProduct.get(index)[1], dataProduct.get(index)[3],
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