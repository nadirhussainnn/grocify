export interface OrderItem {
    productId: number;
    productName: string;
    productDescription: string;
    quantity: number;
  }
  
  export interface Order {
    id: number;
    orderDate: number;
    status: 'PENDING' | 'DELIVERED';
    items: OrderItem[];
  }
  