package board;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardSvc {
	
	final int MAX_ROWS = 10;
	
	public String ArtiList(HttpServletRequest request, HttpServletResponse response) {
		
		Connection con = null;
		
		String pageNo 	= request.getParameter("pageNum");
		String viewPage = "board/list.jsp";
		
		int nPageNo;
		
		if(pageNo == null)
		{
			nPageNo = 1;
		}
		else
		{
			nPageNo = Integer.parseInt(pageNo);
		}
		
		int count = 0;
		
		try {
			
			con = BoardCommon.dbConnect();
		
			BoardDAO boardDao = new BoardDAO(con);
			
			count = boardDao.mulCount();
			if (count > 0) 
			{
				
				ArrayList<BoardDTO> list = 
						boardDao.sltMulti((nPageNo - 1) * MAX_ROWS + 1, MAX_ROWS);

				int pageCount = count / MAX_ROWS + (count % MAX_ROWS == 0 ? 0 : 1);
				int startPage = 1;

				if (nPageNo % 10 != 0)
				{
					startPage = (int)(nPageNo / 10) * 10 + 1;
				}
				else
				{
					startPage = ((int)(nPageNo / 10) - 1) * 10 + 1;
				}

				int pageBlock = 10;
				int endPage = startPage + pageBlock - 1;

				if(endPage > pageCount)
				{
					endPage = pageCount;
				}

				request.setAttribute("STARTPAGE", startPage);
				request.setAttribute("ENDPAGE", endPage);
				request.setAttribute("PAGECOUNT", pageCount);
				request.setAttribute("LIST", list);
				request.setAttribute("CP", nPageNo);
			}
			
			request.setAttribute("COUNT", count);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {BoardCommon.dbClose(con);
		}
		
		return viewPage;
	}
	
	public String artiListOne(HttpServletRequest request, HttpServletResponse response) {
		
		Connection con = null;
		
		String num 		= request.getParameter("num");
		String pageNo 	= request.getParameter("pageNum");
		String viewPage = "board/content.jsp";
		
		int nNum = Integer.parseInt(num);
		int nPageNo;
		
		if(pageNo == null)
		{
			nPageNo = 1;
		}
		else
		{
			nPageNo = Integer.parseInt(pageNo);
		}
		
		try {
			con = BoardCommon.dbConnect();
			
			BoardDAO boardDao = new BoardDAO(con);
			boardDao.updateReadCount(nNum);
			BoardDTO boardDto = boardDao.sltOne(nNum);
			
			if(boardDto == null)
			{
				request.setAttribute("ERR", "해당 자료가 없습니다.");
				return "board/boardErr.jsp";
			}
			
			request.setAttribute("ONELIST", boardDto);
			request.setAttribute("PAGENO", nPageNo);
			
			BoardCommon.commit(con);
		} catch(Exception e) {
			BoardCommon.rollback(con);
			e.printStackTrace();
		} finally {BoardCommon.dbClose(con);
		}
		
		return viewPage;
	}
	
	public String artiInsert(HttpServletRequest request, HttpServletResponse response) {
		
		Connection con = null;

		int num 		= Integer.parseInt((String)request.getParameter("num"));
		int ref 		= Integer.parseInt((String)request.getParameter("ref"));
		int re_step 	= Integer.parseInt((String)request.getParameter("re_step"));
		int re_level 	= Integer.parseInt((String)request.getParameter("re_level"));
		String writer 	= request.getParameter("writer");
		String subject 	= request.getParameter("subject");
		String email 	= request.getParameter("email");
		String content 	= request.getParameter("content");
		String passwd 	= request.getParameter("passwd");
		String ip 		= request.getRemoteAddr();
		String viewPage = "/BoardCtrl?cmd=sltMul";
		
		int number = 0;
		BoardDTO boardDto = null;
		Timestamp ts = null;

		try {
			con = BoardCommon.dbConnect();
			
			BoardDAO boardDao = new BoardDAO(con);
			
			number = boardDao.maxArtiNum();		//맥스글번호 구하기
		
			if(num != 0)
			{
				boardDto = new BoardDTO();
				boardDto.setRef(ref);			//댓글일경우 ref 업뎃처리
				boardDto.setRe_step(re_step);
				
				boardDao.insertArtiRe(boardDto);
				
				re_step = re_step + 1;
				re_level = re_level + 1;
			}
			else
			{
				ref = number;
				re_step = 0;
				re_level = 0;
			}

			boardDto = new BoardDTO();
			boardDto.setNum(number);
			boardDto.setWriter(writer);
			boardDto.setSubject(subject);
			boardDto.setEmail(email);
			boardDto.setContent(content);
			boardDto.setPasswd(passwd);
			ts = new Timestamp(System.currentTimeMillis());
			boardDto.setReg_date(ts);
			boardDto.setIp(ip);
			boardDto.setRef(ref);
			boardDto.setRe_step(re_step);
			boardDto.setRe_level(re_level);
			
			boardDao.insert(boardDto);
			
			BoardCommon.commit(con);
		} catch(Exception e) {
			BoardCommon.rollback(con);
			e.printStackTrace();
		} finally {BoardCommon.dbClose(con);
		}
		
		return viewPage;
	}
	
	public String artiInsertBe(HttpServletRequest request, HttpServletResponse response) {
		
		int num 		= 0;		//새 글일경우
		int ref 		= 1;
		int re_step 	= 0;
		int re_level 	= 0;
		String coment 	= "";
		String viewPage = "board/writeForm.jsp";
		
		try {
			
			if(request.getParameter("num") != null) 		//새 글이 아닐경우
			{
				num 	 = Integer.parseInt(request.getParameter("num"));
				ref 	 = Integer.parseInt(request.getParameter("ref"));
				re_step  = Integer.parseInt(request.getParameter("re_step"));
				re_level = Integer.parseInt(request.getParameter("re_level"));
			}
			
			if(request.getParameter("num") == null)
			{
				coment = "";
			}
			else
			{
				coment = "[답변]";
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		request.setAttribute("NUM", num);
		request.setAttribute("REF", ref);
		request.setAttribute("RE_STEP", re_step);
		request.setAttribute("RE_LEVEL", re_level);
		request.setAttribute("COMENT", coment);
		
		
		return viewPage;
	}
	
	public String artiUpdate(HttpServletRequest request, HttpServletResponse response) {
		
		Connection con = null;
		
		int num 		= Integer.parseInt(request.getParameter("num"));
		String passwd 	= request.getParameter("passwd");
		String writer 	= request.getParameter("writer");
		String subject 	= request.getParameter("subject");
		String email 	= request.getParameter("email");
		String content 	= request.getParameter("content");
		String viewPage = "/BoardCtrl?cmd=sltMul";
		
		BoardDTO dto = null;
		
		
		try {
			con = BoardCommon.dbConnect();
			
			
			BoardDAO boardDao = new BoardDAO(con);
			dto = boardDao.sltOne(num);
			
			if(dto == null)
			{
				request.setAttribute("ERR", "해당 자료가 없습니다.");
				return "board/boardErr.jsp";
			}
			
			if(passwd.equals(dto.getPasswd()))
			{
				;
			}
			else
			{
				request.setAttribute("ERR", "비밀번호가 틀립니다.");
				return "board/boardErr.jsp";
			}
			
			dto = new BoardDTO();
			dto.setNum(num);
			dto.setWriter(writer);
			dto.setPasswd(passwd);
			dto.setSubject(subject);
			dto.setEmail(email);
			dto.setContent(content);
			
			boardDao.updateArti(dto);
			
			BoardCommon.commit(con);
		} catch(Exception e) {
			BoardCommon.rollback(con);
			e.printStackTrace();
		} finally {BoardCommon.dbClose(con);
		}
		
		
		return viewPage;
	}
	
	public String updateBe(HttpServletRequest request, HttpServletResponse response) {
		
		Connection con = null;
		
		String num 		= request.getParameter("num");
		String pageNo 	= request.getParameter("pageNum");
		String viewPage = "board/updateForm.jsp";
		int nNum 		= Integer.parseInt(num);
		
		int nPageNo;
		
		if(pageNo == null)
		{
			nPageNo = 1;
		}
		else
		{
			nPageNo = Integer.parseInt(pageNo);
		}
		
		try {
			con = BoardCommon.dbConnect();
			
			BoardDAO boardDao = new BoardDAO(con);
			
			BoardDTO boardDto = boardDao.sltOne(nNum);
			
			if(boardDto == null)
			{
				request.setAttribute("ERR", "해당 자료가 없습니다.");
				return "board/boardErr.jsp";
			}
			
			request.setAttribute("UPDATE_LIST", boardDto);
			request.setAttribute("UPDATE_PAGE", nPageNo);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {BoardCommon.dbClose(con);
		}
		
		return viewPage;
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		
		Connection con = null;
		
//		String pageNum 	= request.getParameter("pageNum");
		int num 		= Integer.parseInt(request.getParameter("num"));
		String pw 		= request.getParameter("passwd");
		String pwC 		= null;			//비밀번호 확인용
		String viewPage = "/BoardCtrl?cmd=sltMul";
		
		BoardDAO boardDao = null;
		BoardDTO boardDto = null;
		
		
		try {
			con = BoardCommon.dbConnect();
			
			boardDao = new BoardDAO(con);
			boardDto = new BoardDTO();
			boardDto = boardDao.sltOne(num);
			
			if(boardDto == null)
			{
				request.setAttribute("ERR", "해당 자료가 없습니다.");
				return "board/boardErr.jsp";
			}
			
			pwC = boardDto.getPasswd();

			if(pwC.equals(pw))
			{
				boardDao.deleteArti(num);
			}
			else
			{
				request.setAttribute("ERR", "비밀번호가 틀립니다.");
				return "board/boardErr.jsp";
			}
			
			BoardCommon.commit(con);
		} catch(Exception e) {
			BoardCommon.rollback(con);
			e.printStackTrace();
		} finally {BoardCommon.dbClose(con);
		}
		
		return viewPage;
	}
	
	public String deleteBe(HttpServletRequest request, HttpServletResponse response) {
		
		int num 		= Integer.parseInt(request.getParameter("num"));
		int pageNum 	= Integer.parseInt(request.getParameter("pageNum"));
		String viewPage = "board/deleteForm.jsp";
		
		request.setAttribute("NUM", num);
		request.setAttribute("PAGENUM", pageNum);
		
		return viewPage;
	}

}
