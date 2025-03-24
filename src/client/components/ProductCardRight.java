package client.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import backend.services.SalesServices;
import resources.SetPreferences;

public class ProductCardRight extends JPanel {
    private DecimalFormat format = new DecimalFormat("#,###.##");
    private String name = "Default";
    private String price = "89";
    private int amount = 1;
    private int width = 270;
    private int height = 50;
    private String id;
    private String type;
    private Cart modelCart;
    private String[] product;
    private int Stock ;
    private int countStock = 1;

    public ProductCardRight(String id,Cart modelCart) {
        this.id = id;
        product= new SalesServices().genDataProduct(this.id);
        Stock = Integer.parseInt(product[4]);
        genDataProduct();
        this.modelCart = modelCart;
        CreateGui();
    }
    
    public ProductCardRight(int width, int height, String id,Cart modelCart) {
        this.id = id;
        product= new SalesServices().genDataProduct(this.id);
        Stock = Integer.parseInt(product[4]);
        genDataProduct();
        this.width = width;
        this.height = height;
        this.modelCart = modelCart;
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
        nameLabel.setFont(new SetPreferences().getFont(18));
        return nameLabel;
    }

    private JLabel setLabelPrice() {
        JLabel priceLabel = new JLabel(format.format((double) amount * Double.parseDouble(this.price))+ "$");
        priceLabel.setPreferredSize(new Dimension(width / 4, height - 10));
        priceLabel.setHorizontalAlignment(JLabel.LEFT);
        priceLabel.setFont(new SetPreferences().getFont(16));
        priceLabel.setForeground(Color.RED);
        return priceLabel;
    }

    private JPanel setPanelButton() {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JButton minus = new JButton("-");
        JButton plus = new JButton("+");
        JLabel display = new JLabel(String.valueOf(this.amount));
        display.setPreferredSize(new Dimension(20,15));
        display.setHorizontalAlignment(JLabel.CENTER);
        display.setFont(new SetPreferences().getFont(16));
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
                this.countStock--;
                
                if (this.countStock < this.Stock) {
                    plus.setEnabled(true);
                }
                
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
            if (this.countStock <= this.Stock-1) {
                this.amount++;
                this.countStock++;
                display.setText(String.valueOf(this.amount));
                modelCart.setAmountProduct(this.id, getAmount());
                modelCart.setFooter();
                
                if (this.countStock >= this.Stock) {
                    plus.setEnabled(false);
                }
                
                removeAll();
                add(setLabelName());
                add(setLabelPrice());
                add(setPanelButton());
                revalidate();
                repaint();
            }
        });
        if (this.countStock >= this.Stock) {
            plus.setEnabled(false);
        }
        return area;
    }
    

    private void genDataProduct(){
        String[] data = new SalesServices().genDataProduct(this.id);
        this.name = data[1];
        this.type = data[5];
        this.price = data[2];
    }


    public String getAmount(){return String.valueOf(this.amount);}
    public String getPrice(){return String.valueOf(this.price);}
    public String getID(){return this.id;}
    public String getType(){return this.type;}
    public String getName(){return this.name;}
}
