<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/meta_info.htm"%>
<%@ include file="/views/common/common_css.htm"%>
<link type="text/css" rel="stylesheet" href="/third-party/formValidation/css/formValidation.min.css"/>
<link rel="stylesheet" type="text/css" href="/css/front/style.css" title="v"/>
</head>
<body>
	
	<div id="productForm">
	    <div class="form-group">
	        <label>Product name</label>
	        <input type="text" class="form-control" name="name" />
	    </div>
	
	    <div class="form-group">
	        <label>Price</label>
	        <div class="input-group">
	            <div class="input-group-addon">$</div>
	            <input type="text" class="form-control" name="price" />
	        </div>
	    </div>
	
	    <div class="form-group">
	        <label>Size</label>
	        <div class="checkbox">
	            <label><input type="checkbox" name="size[]" value="s" /> S</label>
	        </div>
	        <div class="checkbox">
	            <label><input type="checkbox" name="size[]" value="m" /> M</label>
	        </div>
	        <div class="checkbox">
	            <label><input type="checkbox" name="size[]" value="l" /> L</label>
	        </div>
	        <div class="checkbox">
	            <label><input type="checkbox" name="size[]" value="xl" /> XL</label>
	        </div>
	    </div>
	
	    <div class="form-group">
	        <label>Available in store</label>
	        <div class="radio">
	            <label><input type="radio" name="availability" value="yes" /> Yes</label>
	        </div>
	        <div class="radio">
	            <label><input type="radio" name="availability" value="no" /> No</label>
	        </div>
	    </div>
	
	    <!-- Do NOT use name="submit" or id="submit" for the Submit button -->
	    <button type="submit" class="btn btn-default">Add product</button>
	</div>
	
		
	<%@ include file="/views/common/common_js.htm"%>
	<%@ include file="/views/common/common_front_js.htm"%>
	<script type="text/javascript" src="/third-party/formValidation/js/formValidation.min.js"></script>
	<script type="text/javascript" src="/third-party/formValidation/js/framework/bootstrap.min.js"></script>
	<script>
		$(document).ready(function() {
		    $('#productForm').formValidation({
		        framework: 'bootstrap',
		        icon: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		            name: {
		                validators: {
		                    notEmpty: {
		                        message: 'The name is required'
		                    },
		                    numeric:{
		                    	 message: '必须是数字啊'
		                    },
		                    stringLength: {
		                        min: 6,
		                        max: 30,
		                        message: 'The name must be more than 6 and less than 30 characters long'
		                    },
		                    regexp: {
		                        regexp: /^[a-zA-Z0-9_]+$/,
		                        message: 'The name can only consist of alphabetical, number and underscore'
		                    }
		                }
		            },
		            price: {
		                validators: {
		                    notEmpty: {
		                        message: 'The price is required'
		                    },
		                    numeric: {
		                        message: 'The price must be a number'
		                    }
		                }
		            },
		            'size[]': {
		                validators: {
		                    notEmpty: {
		                        message: 'The size is required'
		                    }
		                }
		            },
		            availability: {
		                validators: {
		                    notEmpty: {
		                        message: 'The availability option is required'
		                    }
		                }
		            }
		        }
		    });
		    
		    var f = $('#productForm').data('formValidation').validate();
		    alert(f.isValid());
		});
	</script>
		
</body>
</html>

