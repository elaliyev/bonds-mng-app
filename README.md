# bonds-mng-app

Small Spring REST application. There are some features which I have implemented. if Somebody has any idea to implement new feature, I am ready! :)  
Some of the 
•	Client can apply for a bond by providing his personal data, term and amount.
•	Default bond coupon (interest rate) is 5% per year and minimal term is 5 years.
•	The application follows regulations that have to be validated in order to preserve fair competition and to prevent potentially illegal operations. A bond can’t be sold if:
    o	the application is made between 10:00 PM and 06:00 AM with an amount higher than 1000
    o	reached max number of sold bonds (e.g. 5) per day from a single IP address
•	The bond is sold if there are no violations of the regulatory requirements. The newly sold bond reference is returned to the client, otherwise the client receives a rejection message.
•	Client should be able to adjust the term of his bond. Each term extension results in coupon decreased by 10% of its value. Shortening the term doesn’t affect the coupon.
•	Client should be able to retrieve whole history of his bonds, including adjustments.
