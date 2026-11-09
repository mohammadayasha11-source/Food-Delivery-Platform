from docx import Document

d = Document()
d.add_heading('MNR UNIVERSITY', 0)
d.add_paragraph('Department of Computer Science and Engineering')
d.add_heading('MINI PROJECT REPORT', 0)
d.add_heading('Food Delivery Platform Simulator', 1)
d.add_paragraph('Java Programming Implementation-Based Mini-Project')
d.add_paragraph('Course: Object Oriented Programming (Java) | B.Tech [Branch], II Year / I Semester')
d.add_paragraph('Submitted by: [Your Name] | Roll Number: 7')
d.add_paragraph('Team members: [Add names and roll numbers]')
d.add_paragraph('Faculty guide: [Faculty Name] | Academic Year 2026-2027')
d.add_page_break()
sections = {
'CERTIFICATE': 'This certifies that this report records the mini project work completed for Object Oriented Programming (Java), MNR University, during academic year 2026-2027. Faculty signature: __________ Date: __________ Place: __________',
'DECLARATION': 'We declare that this report describes our project work. Complete student and team details and verify test results before submission. Student signatures: ____________________.',
'CHAPTER 1: AIM AND ABSTRACT': 'Aim: Build a Java application simulating restaurant ordering, coupon pricing, delivery assignment, order tracking and sales reporting. Abstract: The application models customers, restaurants, menu items, orders and delivery partners. A Swing GUI supports ordering. Collections manage sample data, a worker thread advances order status, a discount interface applies coupons, a custom exception reports closed restaurants, and CSV file I/O saves history.',
'CHAPTER 2: INTRODUCTION AND PROBLEM STATEMENT': 'Food delivery coordinates customers, restaurants and delivery partners. Manual handling can cause incorrect bills, missed availability checks and poor status visibility. This simulator validates a selection, calculates the amount, assigns a partner, updates order state and records the order.',
'CHAPTER 3: OBJECTIVES': 'Apply OOP design; validate orders; calculate coupon and delivery charges; assign available partners; track order lifecycle; save order history; provide a Swing screen.',
'CHAPTER 4: REQUIREMENTS': 'JDK 17+, Swing, CSV storage, Windows/macOS/Linux. Inputs: customer, restaurant, menu item, quantity 1-20 and optional SAVE10 coupon. Closed restaurants and invalid items are rejected. Total is item price times quantity, optionally discounted by 10%, plus $49 delivery.',
'CHAPTER 5: CLASS DESIGN': 'Abstract User is extended by Customer and DeliveryPartner. Restaurant contains MenuItem. FoodOrder refers to Customer, Restaurant, OrderItem and optional DeliveryPartner. DiscountStrategy is implemented by PercentageDiscount. OrderProcessor implements Runnable. Other classes include OrderService, DeliveryService, RestaurantClosedException, OrderStore, DemoData, FoodDeliveryFrame and Main.',
'CHAPTER 6: JAVA CONCEPTS USED': 'Encapsulation and constructors: model classes. Inheritance: User subclasses. Interface/polymorphism: DiscountStrategy. Packages: model, service, exception, ui, util. Custom exception: RestaurantClosedException. Collections: ArrayList, List and Map. Thread/Runnable: OrderProcessor. Synchronization: partner allocation. File I/O: OrderStore. Swing events: FoodDeliveryFrame.',
'CHAPTER 7: PROGRAM LOGIC': 'Load demo records; show order window; select customer, restaurant and item; validate availability; compute subtotal, coupon and delivery charge; assign partner; save order; start status thread; display order and sales information.',
'CHAPTER 8: IMPLEMENTATION': 'Packages are under src/main/java/com/mnr/fooddelivery. Run .\\run.ps1 on Windows or ./run.sh on Unix-like systems. OrderService.place validates, prices, assigns and persists; FoodDeliveryFrame handles GUI events; OrderProcessor updates statuses; OrderStore appends CSV history. Repository: [GitHub URL].',
'CHAPTER 9: OUTPUT SCREENSHOTS': 'Insert screenshots: main order screen; successful order and bill; status progression; closed restaurant message; sales summary and CSV file. Add figure captions.',
'CHAPTER 10: TEST CASES': 'T1 valid order -> confirmation and history row. T2 SAVE10 -> discount before delivery charge. T3 closed restaurant -> rejection. T4 invalid item -> safe error. T5 wait after order -> status progression. T6 inspect CSV -> order saved. Actual results and pass/fail: [Fill after running].',
'CHAPTER 11: RESULTS AND DISCUSSION': 'The source compiles and the Swing screen implements order entry. State changes run in a thread, partner assignment is synchronized, and CSV stores order history. Limitations: seeded records are not persistent; one item per order; first-available partner; no login/database. Future work: cart, database, distance-based matching, ratings and roles.',
'CHAPTER 12: CONCLUSION': 'The project demonstrates a Java food delivery workflow with GUI, OOP models, interface-based discounting, collections, exception handling, synchronization, threads and CSV storage. It provides a base for adding a cart, database and location-based delivery.',
'CHAPTER 13: REFERENCES': 'Herbert Schildt, Java: The Complete Reference. Oracle Java Documentation: https://docs.oracle.com/en/java/. Swing tutorial: https://docs.oracle.com/javase/tutorial/uiswing/. MNR University OOP course notes.',
'APPENDIX A: COMPLETE SOURCE CODE': 'Complete source files are in src/main/java in the linked repository. Attach listings if required by your instructor.',
'APPENDIX B: TEAM CONTRIBUTION': 'Roll 7 | [Your Name] | [Your module/classes] | [Report chapters]. Other team members: [Add names, roll numbers, modules and chapters].'
}
for title, body in sections.items():
    d.add_heading(title, 1)
    d.add_paragraph(body)
d.add_heading('Team Details', 1)
t = d.add_table(rows=1, cols=3)
t.style = 'Light Shading Accent 1'
for c, v in zip(t.rows[0].cells, ['S.No', 'Student Name', 'Roll Number']): c.text = v
for row in [('1','[Your Name]','7'),('2','[Teammate]','[ ]'),('3','[Teammate]','[ ]'),('4','[Teammate]','[ ]'),('5','[Teammate]','[ ]')]:
    cells = t.add_row().cells
    for c, v in zip(cells, row): c.text = v
for s in d.sections: s.footer.paragraphs[0].text = 'Food Delivery Platform Simulator | Roll No. 7'
d.save('Food_Delivery_Platform_Mini_Project_Report.docx')
