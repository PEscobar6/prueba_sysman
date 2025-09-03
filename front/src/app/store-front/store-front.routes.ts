import { Routes } from "@angular/router";
import { StoreFrontLayoutComponent } from './layouts/store-front-layout/store-front-layout.component';
import { HomePageComponent } from "./pages/home-page/home-page.component";
import { MaterialsPageComponent } from "./pages/materials-page/materials-page.component";
import { MaterialPageComponent } from "./pages/material-page/material-page.component";
import { NotFoundPageComponent } from "./pages/not-found-page/not-found-page.component";

export const storeFrontRoutes: Routes = [
    {
        path: '',
        component: StoreFrontLayoutComponent,
        children: [
          {
            path: '',
            component: HomePageComponent
          },
          {
            path: 'materials',
            component: MaterialsPageComponent
          },
          {
            path: 'material/:id',
            component: MaterialPageComponent
          },
          {
            path: '**',
            component: NotFoundPageComponent
          }
        ]
    },
    {
      path: '**',
      redirectTo: ''
    }
];

export default storeFrontRoutes;
