/POS-System
├── /src
│   ├── /client
│   │   ├── MainFrame.java         # Main window of the program, used to switch between panels
│   │   ├── LoginPanel.java        # UI for login (username, password fields)
│   │   ├── RegisterPanel.java     # หน้า UI สำหรับสมัครสมาชิก (สร้างผู้ใช้ใหม่)
│   │   ├── POSPanel.java          # หน้า UI หลักของระบบ POS (เลือกสินค้า, ตะกร้า, ชำระเงิน)
│   │   ├── InventoryPanel.java    # หน้า UI สำหรับจัดการสต็อกสินค้า (ดู, เพิ่ม, แก้ไข)
│   │   └── components
│   │       ├── ProductCard.java   # คอมโพเนนต์ UI แสดงข้อมูลสินค้า (เช่น ชื่อ, ราคา)
│   │       └── CartTable.java     # คอมโพเนนต์ UI แสดงตะกร้าสินค้าแบบตาราง
│   ├── /backend
│   │   ├── /models
│   │   │   ├── User.java         # โมเดลผู้ใช้ (เก็บ username, password, role)
│   │   │   ├── Product.java      # โมเดลสินค้า (เก็บชื่อ, ราคา, จำนวน)
│   │   │   └── Transaction.java  # โมเดลการซื้อขาย (เก็บรายละเอียดการซื้อ)
│   │   ├── /services
│   │   │   ├── AuthService.java  # ล็อกอินและสมัครสมาชิกของลูกค้า (ตรวจสอบ, สร้างผู้ใช้)
│   │   │   ├── InventoryService.java # จัดการสต็อก (เพิ่ม, ลบ, อัปเดตสินค้า)
│   │   │   └── SalesService.java    # การซื้อขาย (คำนวณ, บันทึกการซื้อ)
│   │   ├── /data
│   │   │   ├── DataManager.java  # จัดการข้อมูล (เซฟ/โหลดข้อมูลไปไฟล์)
│   │   │   └── InMemoryStore.java# ประวัติการทำรายการ
│   │   └── /utils
│   │       ├── Logger.java       # เก็บฟังก์ชันบันทึก log
│   │       └── Formatter.java    # เก็บฟังก์ชันจัดรูปแบบ (เช่น ราคา, วันที่)
│   └── Main.java                  # จุดเริ่มต้นโปรแกรม (เรียก GUI)
├── /resources                     # พวกรูป
└── README.md                      # คำอธิบายโปรเจค