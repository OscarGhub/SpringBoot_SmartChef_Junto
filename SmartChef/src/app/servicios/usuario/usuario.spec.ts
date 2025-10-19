import { TestBed } from '@angular/core/testing';

import { UsuarioModel } from './usuario.model';

describe('UsuarioModel', () => {
  let service: UsuarioModel;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsuarioModel);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
