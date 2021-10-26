<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />
		
		
		
		<div class="row py-3">
			<div class="col-4"><h3>問題新規作成</h3> </div>
		</div>
		
		
		
		
		<form action="./create" method="post">
			<table class="table">
				
				
				<tr>
					<th class="bg-light">問題文</th>
					<td>
						<textarea  name="new_sentence" required cols="80" rows="10" class="form-control" autofocus></textarea>
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">解答例</th>
					<td>
						<textarea  name="new_answer" required cols="80" rows="5" class="form-control"></textarea>
					</td>
				</tr>
				
				<tr>
					<th></th>
					<td><input type="submit" value="投稿" class="btn btn-success"></td>
				</tr>
			</table>
			
			
		</form>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>