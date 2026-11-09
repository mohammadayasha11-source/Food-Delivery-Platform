package com.mnr.fooddelivery.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import com.mnr.fooddelivery.util.*;
import com.mnr.fooddelivery.model.*;
import com.mnr.fooddelivery.service.*;
import com.mnr.fooddelivery.exception.*;

/** Clean, desktop-style interface for the food delivery simulator. */
public class FoodDeliveryFrame extends JFrame {
    private static final Color NAVY = new Color(18,35,63), BLUE = new Color(37,99,235), BLUE_SOFT = new Color(239,246,255), TEXT = new Color(31,41,55), MUTED = new Color(100,116,139), BORDER = new Color(226,232,240), GREEN = new Color(22,163,74);
    private final DemoData data; private final OrderService service;
    private final JComboBox<String> customer = new JComboBox<>(), restaurant = new JComboBox<>(), item = new JComboBox<>();
    private final JSpinner qty = new JSpinner(new SpinnerNumberModel(1,1,20,1)); private final JTextField coupon = new JTextField();
    private final JTextArea output = new JTextArea(); private final JLabel status = new JLabel("Ready to take your order"); private final JLabel itemHint = new JLabel("Select a restaurant to view its menu");

    public FoodDeliveryFrame(DemoData d, OrderService s) { super("Foodly | Campus Food Delivery"); data=d; service=s; setDefaultCloseOperation(EXIT_ON_CLOSE); setMinimumSize(new Dimension(980,680)); setSize(1050,720); setLocationRelativeTo(null); setIconImage(createLogoImage(64)); buildUi(); }

    private void buildUi() {
        JPanel root=new JPanel(new BorderLayout()); root.setBackground(new Color(248,250,252)); root.setBorder(new EmptyBorder(0,0,12,0)); root.add(header(),BorderLayout.NORTH);
        JPanel content=new JPanel(new GridBagLayout()); content.setOpaque(false); content.setBorder(new EmptyBorder(26,34,18,34));
        content.add(orderCard(), gbc(0,0,0.68,1,14)); content.add(sideCard(), gbc(1,0,0.32,1,14)); root.add(content,BorderLayout.CENTER); root.add(footer(),BorderLayout.SOUTH); setContentPane(root);
    }
    private JPanel header() {
        JPanel bar=new JPanel(new BorderLayout(16,0)); bar.setBackground(Color.WHITE); bar.setBorder(new EmptyBorder(22,34,20,34));
        JLabel logo=new JLabel("  FOODLY"); logo.setIcon(new ImageIcon(createLogoImage(42))); logo.setFont(new Font("SansSerif",Font.BOLD,23)); logo.setForeground(NAVY);
        JLabel tagline=new JLabel("Fresh meals. Simple ordering. Happier campus."); tagline.setFont(new Font("SansSerif",Font.PLAIN,14)); tagline.setForeground(MUTED);
        JLabel date=new JLabel(java.time.LocalDate.now().toString()); date.setFont(new Font("SansSerif",Font.PLAIN,13)); date.setForeground(MUTED); bar.add(logo,BorderLayout.WEST); bar.add(tagline,BorderLayout.CENTER); bar.add(date,BorderLayout.EAST); return bar;
    }
    private JPanel orderCard() {
        JPanel card=cardPanel(); card.setLayout(new BorderLayout(0,18)); JPanel title=new JPanel(new BorderLayout()); title.setOpaque(false);
        JPanel titles=new JPanel(); titles.setOpaque(false); titles.setLayout(new BoxLayout(titles,BoxLayout.Y_AXIS)); titles.add(heading("Place an order",22,NAVY)); titles.add(Box.createVerticalStrut(5)); JLabel sub=new JLabel("Choose your meal and we’ll take care of the rest."); sub.setForeground(MUTED); sub.setFont(new Font("SansSerif",Font.PLAIN,13)); titles.add(sub); title.add(titles,BorderLayout.WEST); card.add(title,BorderLayout.NORTH);
        for(Customer c:data.customers) customer.addItem(c.getId()+"  ·  "+c.getName()); for(Restaurant r:data.restaurants) restaurant.addItem(r.getId()+"  ·  "+r.getName()+(!r.isOpen()?"  (closed)":"")); restaurant.addActionListener(e->loadItems()); styleCombo(customer); styleCombo(restaurant); styleCombo(item); styleSpinner(qty); styleText(coupon); coupon.setToolTipText("Try SAVE10 for a 10% discount"); itemHint.setForeground(MUTED); itemHint.setFont(new Font("SansSerif",Font.ITALIC,12));
        JPanel form=new JPanel(new GridBagLayout()); form.setOpaque(false); addField(form,"Customer",customer,0,null); addField(form,"Restaurant",restaurant,1,null); addField(form,"Menu item",item,2,itemHint); addField(form,"Quantity",qty,3,null); addField(form,"Coupon",coupon,4,new JLabel("Optional · SAVE10")); loadItems();
        JButton place=primaryButton("Place order"); place.addActionListener(e->placeOrder()); JButton clear=secondaryButton("Clear"); clear.addActionListener(e->{coupon.setText("");qty.setValue(1);output.setText("");status.setText("Ready to take your order");}); JPanel buttons=new JPanel(new FlowLayout(FlowLayout.LEFT,10,0)); buttons.setOpaque(false); buttons.add(place); buttons.add(clear);
        JPanel center=new JPanel(new BorderLayout(0,18)); center.setOpaque(false); center.add(form,BorderLayout.NORTH); center.add(buttons,BorderLayout.SOUTH); card.add(center,BorderLayout.CENTER); return card;
    }
    private JPanel sideCard() {
        JPanel card=cardPanel(); card.setLayout(new BorderLayout(0,16)); card.add(heading("Order activity",19,NAVY),BorderLayout.NORTH);
        output.setEditable(false); output.setLineWrap(true); output.setWrapStyleWord(true); output.setFont(new Font("SansSerif",Font.PLAIN,13)); output.setForeground(TEXT); output.setBackground(new Color(248,250,252)); output.setBorder(new EmptyBorder(12,12,12,12)); JScrollPane scroll=new JScrollPane(output); scroll.setBorder(BorderFactory.createLineBorder(BORDER)); scroll.getVerticalScrollBar().setUnitIncrement(14); card.add(scroll,BorderLayout.CENTER);
        JPanel bottom=new JPanel(new BorderLayout(0,10)); bottom.setOpaque(false); status.setForeground(GREEN); status.setFont(new Font("SansSerif",Font.BOLD,13)); JButton report=secondaryButton("View sales report"); report.addActionListener(e->showReport()); bottom.add(status,BorderLayout.NORTH); bottom.add(report,BorderLayout.SOUTH); card.add(bottom,BorderLayout.SOUTH); append("Welcome to Foodly. Sample menus are ready."); return card;
    }
    private JPanel footer(){ JPanel p=new JPanel(new BorderLayout()); p.setOpaque(false); p.setBorder(new EmptyBorder(0,36,0,36)); JLabel l=new JLabel("Foodly Desktop  •  Demo environment  •  Orders are saved locally"); l.setForeground(MUTED); l.setFont(new Font("SansSerif",Font.PLAIN,12)); p.add(l,BorderLayout.WEST); return p; }
    private void addField(JPanel p,String label,JComponent component,int row,JComponent hint){ GridBagConstraints a=new GridBagConstraints();a.gridx=0;a.gridy=row;a.weightx=0;a.fill=GridBagConstraints.HORIZONTAL;a.anchor=GridBagConstraints.NORTHWEST;a.insets=new Insets(6,0,6,18); JLabel l=new JLabel(label);l.setForeground(TEXT);l.setFont(new Font("SansSerif",Font.BOLD,13));p.add(l,a); GridBagConstraints b=new GridBagConstraints();b.gridx=1;b.gridy=row;b.weightx=1;b.fill=GridBagConstraints.HORIZONTAL;b.insets=new Insets(4,0,4,0);p.add(component,b); if(hint!=null){GridBagConstraints c=new GridBagConstraints();c.gridx=2;c.gridy=row;c.insets=new Insets(4,10,4,0);p.add(hint,c);} }
    private void loadItems(){ item.removeAllItems(); int index=restaurant.getSelectedIndex(); if(index<0)return; Restaurant r=data.restaurants.get(index); for(com.mnr.fooddelivery.model.MenuItem m:r.getMenu()) item.addItem(m.getId()+"  ·  "+m.getName()+"  ($"+String.format("%.2f",m.getPrice())+")"); itemHint.setText(r.isOpen()?"Open for orders":"Currently closed"); itemHint.setForeground(r.isOpen()?GREEN:new Color(220,38,38)); }
    private void placeOrder(){
        try {
            Customer c=data.customers.get(customer.getSelectedIndex()); Restaurant r=data.restaurants.get(restaurant.getSelectedIndex());
            if(!r.isOpen()) throw new RestaurantClosedException(r.getName()+" is currently closed.");
            String iid=((String)item.getSelectedItem()).split("  ·  ")[0]; com.mnr.fooddelivery.model.MenuItem selected=r.findItem(iid);
            if(selected==null) throw new IllegalArgumentException("Please choose a menu item.");
            int count=(Integer)qty.getValue(); double subtotal=selected.getPrice()*count; double discount="SAVE10".equalsIgnoreCase(coupon.getText().trim())?subtotal*.10:0; double total=subtotal-discount+49;
            showPayment(c,r,iid,count,coupon.getText().trim(),total,selected.getName());
        } catch(RestaurantClosedException|RuntimeException ex){status.setText("Could not start checkout");status.setForeground(new Color(220,38,38));JOptionPane.showMessageDialog(this,ex.getMessage(),"Unable to continue",JOptionPane.WARNING_MESSAGE);}
    }
    private void showPayment(Customer c, Restaurant r, String iid, int count, String code, double total, String foodName) {
        JDialog dialog=new JDialog(this,"Foodly · Demo checkout",true); dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel panel=new JPanel(new BorderLayout(14,14)); panel.setBackground(new Color(248,250,252)); panel.setBorder(new EmptyBorder(22,26,22,26));
        JLabel title=heading("Complete your payment",22,NAVY); JLabel caption=new JLabel("Demo checkout · no real payment is processed"); caption.setForeground(MUTED); caption.setFont(new Font("SansSerif",Font.PLAIN,13));
        JPanel top=new JPanel();top.setOpaque(false);top.setLayout(new BoxLayout(top,BoxLayout.Y_AXIS));top.add(title);top.add(Box.createVerticalStrut(5));top.add(caption);panel.add(top,BorderLayout.NORTH);
        JPanel center=new JPanel(new GridBagLayout());center.setOpaque(false);JLabel summary=new JLabel("<html><b>"+foodName+"</b> × "+count+"<br>Restaurant: "+r.getName()+"<br><br><font size='+2' color='#193763'>Amount: $"+String.format("%.2f",total)+"</font></html>");summary.setFont(new Font("SansSerif",Font.PLAIN,14));center.add(summary);panel.add(center,BorderLayout.WEST);
        int scannerId=ThreadLocalRandom.current().nextInt(100000,999999); JPanel scan=new JPanel(new BorderLayout(5,8));scan.setBackground(Color.WHITE);scan.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER),new EmptyBorder(12,12,12,12)));
        JLabel scTitle=new JLabel("DEMO SCANNER · NOT PAYABLE",SwingConstants.CENTER);scTitle.setForeground(new Color(180,83,9));scTitle.setFont(new Font("SansSerif",Font.BOLD,11)); JPanel qr=new JPanel(new BorderLayout());qr.setPreferredSize(new Dimension(190,190));qr.setBackground(Color.WHITE);qr.add(new JLabel(new ImageIcon(createDemoScanner(scannerId,190))),BorderLayout.CENTER); JLabel ref=new JLabel("Demo reference #"+scannerId,SwingConstants.CENTER);ref.setForeground(MUTED);ref.setFont(new Font("SansSerif",Font.PLAIN,11));scan.add(scTitle,BorderLayout.NORTH);scan.add(qr,BorderLayout.CENTER);scan.add(ref,BorderLayout.SOUTH);panel.add(scan,BorderLayout.EAST);
        JLabel timerLabel=new JLabel("Time remaining: 60 seconds");timerLabel.setFont(new Font("SansSerif",Font.BOLD,14));timerLabel.setForeground(BLUE);
        JButton paid=primaryButton("Payment done · place order"); JButton cancel=secondaryButton("Cancel checkout"); JPanel actions=new JPanel(new BorderLayout(0,12));actions.setOpaque(false);JPanel row=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));row.setOpaque(false);row.add(cancel);row.add(paid);actions.add(timerLabel,BorderLayout.NORTH);actions.add(row,BorderLayout.SOUTH);panel.add(actions,BorderLayout.SOUTH);
        final int[] seconds={60}; Timer timer=new Timer(1000,e->{seconds[0]--;if(seconds[0]<=0){((Timer)e.getSource()).stop();paid.setEnabled(false);timerLabel.setText("Checkout expired · no order was placed");timerLabel.setForeground(new Color(220,38,38));new Timer(1200,ev->{dialog.dispose();}).start();}else timerLabel.setText("Time remaining: "+seconds[0]+" seconds");});
        cancel.addActionListener(e->{timer.stop();dialog.dispose();}); paid.addActionListener(e->{timer.stop();paid.setEnabled(false);try{FoodOrder o=service.place(c,r,iid,count,code);append("Payment confirmed (demo) · Order "+o.getId()+" · $"+String.format("%.2f",o.getTotal()));status.setText("Order "+o.getId()+" placed · "+o.getStatus());status.setForeground(GREEN);new Thread(new OrderProcessor(o,()->SwingUtilities.invokeLater(()->{append("Order "+o.getId()+" status: "+o.getStatus());status.setText("Order "+o.getId()+" · "+o.getStatus());})),"order-"+o.getId()).start();dialog.dispose();}catch(RestaurantClosedException|RuntimeException ex){dialog.dispose();JOptionPane.showMessageDialog(this,ex.getMessage(),"Order not placed",JOptionPane.WARNING_MESSAGE);}});
        dialog.setContentPane(panel);dialog.pack();dialog.setMinimumSize(new Dimension(640,390));dialog.setLocationRelativeTo(this);timer.start();dialog.setVisible(true);timer.stop();
    }
    private static BufferedImage createDemoScanner(int seed,int size){BufferedImage img=new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);Graphics2D g=img.createGraphics();g.setColor(Color.WHITE);g.fillRect(0,0,size,size);java.util.Random random=new java.util.Random(seed);int cell=8;for(int y=0;y<size/cell;y++)for(int x=0;x<size/cell;x++){boolean finder=(x<7&&y<7)||(x>size/cell-8&&y<7)||(x<7&&y>size/cell-8);boolean dark=finder?((x%6==0||x%6==5||y%6==0||y%6==5)||((x%6>=2&&x%6<=3)&&(y%6>=2&&y%6<=3))):random.nextBoolean();if(dark){g.setColor(new Color(28,39,58));g.fillRect(x*cell,y*cell,cell-1,cell-1);}}g.dispose();return img;}
    private void showReport(){ append("Sales summary: "+service.salesByRestaurant()); append("Order history: data/orders.csv"); }
    private void append(String s){ output.append("• "+s+"\n"); output.setCaretPosition(output.getDocument().getLength()); }
    private static JPanel cardPanel(){ JPanel p=new JPanel();p.setBackground(Color.WHITE);p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER),new EmptyBorder(22,24,22,24)));return p; }
    private static JLabel heading(String s,int size,Color c){ JLabel l=new JLabel(s);l.setFont(new Font("SansSerif",Font.BOLD,size));l.setForeground(c);return l; }
    private static GridBagConstraints gbc(int x,int y,double wx,double wy,int right){ GridBagConstraints g=new GridBagConstraints();g.gridx=x;g.gridy=y;g.weightx=wx;g.weighty=wy;g.fill=GridBagConstraints.BOTH;g.insets=new Insets(0,x==0?0:right,0,0);return g; }
    private static void styleCombo(JComboBox<?> c){ c.setFont(new Font("SansSerif",Font.PLAIN,13));c.setBackground(Color.WHITE);c.setForeground(TEXT);c.setBorder(BorderFactory.createLineBorder(BORDER));c.setPreferredSize(new Dimension(300,38)); }
    private static void styleSpinner(JSpinner s){ s.setFont(new Font("SansSerif",Font.PLAIN,13));s.setPreferredSize(new Dimension(100,38)); }
    private static void styleText(JTextField f){ f.setFont(new Font("SansSerif",Font.PLAIN,13));f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER),new EmptyBorder(9,10,9,10)));f.setPreferredSize(new Dimension(200,38)); }
    private static JButton primaryButton(String s){ JButton b=new JButton(s);b.setFont(new Font("SansSerif",Font.BOLD,13));b.setForeground(Color.WHITE);b.setBackground(BLUE);b.setFocusPainted(false);b.setBorder(new EmptyBorder(11,22,11,22));return b; }
    private static JButton secondaryButton(String s){ JButton b=new JButton(s);b.setFont(new Font("SansSerif",Font.BOLD,12));b.setForeground(BLUE);b.setBackground(BLUE_SOFT);b.setFocusPainted(false);b.setBorder(new EmptyBorder(10,16,10,16));return b; }
    private static BufferedImage createLogoImage(int size){ BufferedImage i=new BufferedImage(size,size,BufferedImage.TYPE_INT_ARGB);Graphics2D g=i.createGraphics();g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g.setColor(BLUE);g.fillRoundRect(0,0,size,size,size/4,size/4);g.setColor(Color.WHITE);g.fillOval(size/4,size/4,size/2,size/2);g.setColor(BLUE);g.fillOval(size*3/8,size*3/8,size/4,size/4);g.dispose();return i; }
}
