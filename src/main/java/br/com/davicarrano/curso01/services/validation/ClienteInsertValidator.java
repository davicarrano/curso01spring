package br.com.davicarrano.curso01.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.davicarrano.curso01.domains.ClienteNewDTO;
import br.com.davicarrano.curso01.resources.exception.FieldErrorCustom;
import br.com.davicarrano.curso01.services.utils.ValidaCpfCnpj;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public boolean isValid(ClienteNewDTO objNewDTO, ConstraintValidatorContext context) {
		List<FieldErrorCustom> listaErros = new ArrayList();
		
		if (objNewDTO.getTipo() == 1 && !ValidaCpfCnpj.isValidCPF(objNewDTO.getCpfOuCnpj())) {
			listaErros.add(new FieldErrorCustom("CpfOuCnpj", "CPF Invalido"));
		}

		if (objNewDTO.getTipo() == 2 && !ValidaCpfCnpj.isValidCNPJ(objNewDTO.getCpfOuCnpj())) {
			listaErros.add(new FieldErrorCustom("CpfOuCnpj", "CNPJ Invalido"));
		}
		

		for (FieldErrorCustom fieldErrorCustom : listaErros) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldErrorCustom.getMsg()).addPropertyNode(fieldErrorCustom.getCampo()).addConstraintViolation();
		}
		
		return listaErros.isEmpty();
	}

}
