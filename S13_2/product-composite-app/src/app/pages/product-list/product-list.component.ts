import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ProductCompositeService } from '../../api/api/productComposite.service';
import { KeycloakService } from 'keycloak-angular';
import { ProductAggregateDto } from '../../api/model/productAggregateDto';
import { ReviewSummaryDto } from '../../api/model/reviewSummaryDto';
import { RecommendationSummaryDto } from '../../api/model/recommendationSummaryDto';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css','../../app.component.css']
})
export class ProductListComponent implements OnInit {
  detailsMap: Record<number, ProductAggregateDto> = {};

  newProduct: ProductAggregateDto = { name: '', weight: 0, reviews: [], recommendations: [] };
  newReviews: ReviewSummaryDto[] = [];
  newRecommendations: RecommendationSummaryDto[] = [];
  idProduct: number | null = null;

  constructor(private service: ProductCompositeService,
              private keycloak: KeycloakService) {}

  ngOnInit() {
    
  }

  getProduct(id: number) {
    this.service.getProduct(id).subscribe(p => this.detailsMap[id] = p);
  }

  toggleDetails(id: number) {
    if (this.detailsMap[id]) {
      delete this.detailsMap[id];
    } else {
      this.service.getProduct(id).subscribe(p => this.detailsMap[id] = p);
    }
  }

  deleteProduct(id: number) {
    this.service.deleteProduct(id).subscribe(() => delete this.detailsMap[id]);
  }

  addReview() {
    this.newReviews.push({});
  }
  removeReview(i: number) {
    this.newReviews.splice(i, 1);
  }

  addRecommendation() {
    this.newRecommendations.push({});
  }
  removeRecommendation(i: number) {
    this.newRecommendations.splice(i, 1);
  }

  /** Maneja el submit del formulario */
  createProduct(form: NgForm) {
    const dto: ProductAggregateDto = {
      ...this.newProduct,
      reviews: this.newReviews,
      recommendations: this.newRecommendations
    };
    this.service.createProductAggregate(dto).subscribe(() => {
      form.resetForm({ product: { productId: null, name: '', weight: 0 } });
      this.newReviews = [];
      this.newRecommendations = [];
    });
  }

  /** Cierra sesión en Keycloak y redirige al login */
  logout() {
    this.keycloak.logout();
  }
} 