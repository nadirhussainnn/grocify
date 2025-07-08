export interface OrderItem {
    productId: number;
    productName: string;
    productDescription: string;
    quantity: number;
    priceAtPurchase: number;
  }
  
  export interface Order {
    id: number;
    orderDate: number;
    status: 'PENDING' | 'DELIVERED';
    items: OrderItem[];
  }
  