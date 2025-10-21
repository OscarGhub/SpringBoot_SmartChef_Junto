import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { UsuarioService } from './usuario.service';
import { Usuario } from './usuario.model';

describe('UsuarioService', () => {
  let service: UsuarioService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UsuarioService],
    });

    service = TestBed.inject(UsuarioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch usuarios', () => {
    const dummyUsuarios: Usuario[] = [
      {
        id: 1, nombre: 'Test User', correo_electronico: 'test@example.com', contrasena: '1234',
        fecha_nacimiento: ""
      },
    ];

    service.getUsuarios().subscribe(usuarios => {
      expect(usuarios.length).toBe(1);
      expect(usuarios).toEqual(dummyUsuarios);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/usuario');
    expect(req.request.method).toBe('GET');
    req.flush(dummyUsuarios);
  });

});
