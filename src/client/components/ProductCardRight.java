package client.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import backend.services.InventoryService;
import resources.SetPreferences;
import resources.Tools;


public class ProductCardRight extends JPanel {
    private String name = "Default";
    private String price = "89";
    private int amount = 1;
    private int width = 290;
    private int height = 50;
    private String id;
    private String type;
    private Cart modelCart;

    public ProductCardRight(String name, String price,Cart modelCart) {
        this.name = name;
        this.price = price;
        this.modelCart = modelCart;
        genID();
        CreateGui();
    }
    
    public ProductCardRight(int width, int height, String name, String price,Cart modelCart) {
        this.name = name;
        this.price = price;
        this.width = width;
        this.height = height;
        this.modelCart = modelCart;
        genID();
        CreateGui();
    }

    private void CreateGui() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(width, height));
        setBorder(new LineBorder(Color.BLACK));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 4));
        add(setLabelName());
        add(setLabelPrice());
        add(setPanelButton());
    }

    private JLabel setLabelName() {
        JLabel nameLabel = new JLabel(this.name);
        nameLabel.setPreferredSize(new Dimension(width / 4, height - 10));
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        nameLabel.setFont(new SetPreferences().getFont(12));
        return nameLabel;
    }

    private JLabel setLabelPrice() {
        JLabel priceLabel = new JLabel(String.valueOf((double) amount * Double.parseDouble(this.price))+ "$");
        priceLabel.setPreferredSize(new Dimension(width / 4, height - 10));
        priceLabel.setHorizontalAlignment(JLabel.LEFT);
        priceLabel.setFont(new SetPreferences().getFont(10));
        priceLabel.setForeground(Color.RED);
        return priceLabel;
    }

    private JPanel setPanelButton() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton minus = new JButton("-");
        JButton plus = new JButton("+");
        JLabel display = new JLabel(String.valueOf(this.amount));
        display.setPreferredSize(new Dimension(20,15));
        display.setHorizontalAlignment(JLabel.CENTER);
        display.setFont(new SetPreferences().getFont(10));
        area.setBackground(Color.WHITE);
        area.add(minus);
        area.add(display);
        area.add(plus);
        minus.setBackground(null);
        plus.setBackground(null);
        minus.setOpaque(true);
        minus.setContentAreaFilled(false);
        plus.setOpaque(true);
        plus.setContentAreaFilled(false);

        minus.addActionListener(e -> {
            String store = display.getText();
            if (!store.equals("1")) {
                this.amount = this.amount - 1;
                display.setText("");
                display.setText(String.valueOf(this.amount));
                modelCart.setAmountProduct(this.id,getAmount());
                modelCart.setFooter();
                removeAll();
                add(setLabelName());
                add(setLabelPrice());
                add(setPanelButton());
                revalidate();
                repaint();
            }else if(store.equals("1")){
                this.amount = this.amount - 1;
                modelCart.setAmountProduct(this.id,getAmount());
                modelCart.setFooter();
                modelCart.deleteProduct(this);
            }
        });
        
        plus.addActionListener(e -> {
            this.amount = this.amount + 1;
            display.setText("");
            display.setText(String.valueOf(this.amount));
            modelCart.setAmountProduct(this.id,getAmount());
            modelCart.setFooter();
            removeAll();
            add(setLabelName());
            add(setLabelPrice());
            add(setPanelButton());
            revalidate();
            repaint();
        });

        return area;
    }
    

    private void genID(){
        String[][] data = new InventoryService().getAllProductData();
        genType();
        for (String[] recode : data){
            if (new Tools().LinearSearch(recode, this.name)){
                this.id = recode[0];
            }
        }
    }
    
    private void genType(){
        String[][] data = new InventoryService().getAllProductData();

        for (String[] recode : data){
            if (new Tools().LinearSearch(recode, this.name)){
                this.type = recode[6];
            }
        }
    }

    public String getAmount(){return String.valueOf(this.amount);}
    public String getPrice(){return String.valueOf(this.price);}
    public String getID(){return this.id;}
    public String getType(){return this.type;}
    public String getName(){return this.name;}
}
