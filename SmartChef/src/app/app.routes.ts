import { Routes } from '@angular/router';
import {InicioComponent} from "./inicio/inicio.component";
import {PerfilComponent} from "./perfil/perfil.component";
import {GuardadosComponent} from "./guardados/guardados.component";
import {CarritoComponent} from "./carrito/carrito.component";
import {VerRecetaComponent} from "./ver-receta/ver-receta.component";
import {LoginComponent} from "./login/login.component";
import {LoginRegistroComponent} from "./login-registro/login-registro.component";
import {RegistroComponent} from "./registro/registro.component";
import {InventarioComponent} from "./inventario/inventario.component";

export const routes: Routes = [

  {path: '', redirectTo: 'inicio', pathMatch: 'full'},

  {path: 'registro_login', component: LoginRegistroComponent},

  {path: 'registro', component: RegistroComponent},

  {path: 'login', component: LoginComponent},

  {path: 'inicio', component: InicioComponent},

  {path: 'perfil', component: PerfilComponent},

  {path: 'guardados', component: GuardadosComponent},

  {path: 'carrito', component: CarritoComponent},

  {path: 'receta', component: VerRecetaComponent},

  {path: 'inventario', component: InventarioComponent},

];
