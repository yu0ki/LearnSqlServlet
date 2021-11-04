<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_admin.jsp" flush="true" />

		<div class="row py-3">
			<div class="col-4"><h3>告知一覧</h3></div>
			<div class="col-5"></div>
			<div><a href="/LearnSqlServlet/admins/announcement/create.jsp" class="btn btn-success">新規作成</a></div>
		</div>
		
		<!-- 告知一覧を取得 -->
		<% announcement_dao.AdminAnnouncementIndexDAO aaid = new announcement_dao.AdminAnnouncementIndexDAO();  %>
		<% java.util.List<beans.AdminAnnouncementBeans> aanb_list = aaid.findAllAnnouncement(); %>
		
		<table class="table">
			<tr class="row">
				<th class="col-2 bg-light">告知id</th>
				<th class="col-8 bg-light">タイトル</th>
			</tr>
		
			<%for(int i = 0; i < aanb_list.size(); i++){%>
		        <% beans.AdminAnnouncementBeans aanb = aanb_list.get(i);%>
		        <tr class="row">
		            <td class="col-2">
		            	<a href="/LearnSqlServlet/admins/announcement/show.jsp?aid=<%= aanb.getAid() %>"><%= aanb.getAid() %></a>
		            </td>
		            
		            
		            <!-- style直指定。タイトルが長すぎる場合は三点リーダーで省略 -->
		            <td style="max-width: 100%; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" class="wrap-text col-8">
		            	<%= aanb.getTitle() %>
		            </td>
		        </tr>
		    <% } %>
			
		</table>
		
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>