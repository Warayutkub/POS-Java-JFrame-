Permission
    Manager 
        -Dashboard
        -ManageStock
        -ManageProduct
        -RegisterEmployee
    Employee
        -Sale
        -receipt

Data Structure
    Employee
        id,name,phone,email,password,permission,role,tackSaleDay(bath),trackSalesTotal(Bath)
    User
        id,name,phone,email,password
    Product
        id,name,price,discount,stock,type,PathImage
    InMemory(History)
        BillId,typeProduct,date,time,costumerName,productName,QTY,total(Bath) 


Type Product
    1 : Electronic
    2 : Food
    3 : Fashion
    4 : Cosmetic
    5 : Household
    6 : Tool
    7 : Sport
    8 : Toy
    