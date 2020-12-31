# ERROR for float (<, >)
from pathlib import Path
import shutil,os,sys,datetime

def main() -> None:
  '''Contains the while loop for the first input choice D and R''' 
  first_input = input()
  invalid_input = True
  while invalid_input == True:
    real_final_list = []
    if first_input.startswith('D '):
      lst = first_input.split()
      path1 = ''.join(first_input[2:])
      path = Path(path1)
      if path.exists():
        invalid_input = False
        d_input(path)
      else:
        print("ERROR")
        first_input = input()
    elif first_input.startswith('R '):
      lst = first_input.split()
      path1 = ''.join(first_input[2:])
      path = Path(path1)
      
      if path.exists():
        result_list = r_input(path)
        for element in result_list:
          print(element)
        second_choice(result_list)
        invalid_input = False
      else:
        print("ERROR")
        first_input = input()
    else:
      print("ERROR")
      first_input = input()
  
  
def d_input(path: Path) -> None:
  '''Contains and prints all the paths of the files in the first directory'''
  list2 = []
  if path.is_dir():
      lst2 = list(path.iterdir())
      for i in lst2:
          p = Path(i)
          if p.is_file():
              list2.append(p)
              print(p)
          else:
              pass
  second_choice(list2)
              
    
def r_input(path: Path) -> [Path]:
  '''Contains and prints all the paths of the files in the first directory and subdirectories'''
  directorylist = []
  lst = sorted(path.iterdir())
  for path in lst:
     if path.is_file():
          directorylist.append(path)
  for path in lst:
      if path.is_dir():
          directorylist.extend(r_input(path))
  return directorylist
  
def second_choice(list2: [Path]) -> None:
  '''Contains the while loop for the second input choice of A,N,E,T,<,> in order to narrow the search'''
  invalid_input = True
  second_input = input()
  while invalid_input == True:
    try:
      second_input_list = second_input.split()
      if second_input_list[0] in ["A","N","E","T","<", ">"]:
        if second_input in ["N", "E", "T", "<", ">"]:
          invalid_input = True
          print('ERROR')
          second_input = input()

        else:
          if second_input.startswith('A'):
            if second_input == 'A':
              interesting_file_list = a_input(list2)
              invalid_input = False
            else:
              print('ERROR')
              invalid_input = True
              second_input = input()
          elif second_input.startswith('N '):
            second_lst = second_input.split()
            second_path = second_input[2:]
            second_path2 = Path(second_path)
            if len(second_lst) == 1:
              print('ERROR')
              second_input = input()
            else:
              '''
              for element in list2:
                if second_path in str(element):
                  second_path2 = list2[list2.index(element)]
              '''
              if second_path2.exists() and second_path2 != '':
                interesting_file_list = n_input(list2, second_path)
                invalid_input = False
                if interesting_file_list == []:
                  sys.exit()
              elif second_path2 == '':
                print("ERROR")
              else:
                sys.exit()
          elif second_input.startswith('E '):
            if second_input == 'E ':
              sys.exit()
            else:
              second_lst = second_input.split()
              second_path = ''.join(second_input[2:])
              interesting_file_list = e_input(second_path, list2)
              invalid_input = False
              if interesting_file_list == []:
                sys.exit()
          elif second_input.startswith('T '):
            if second_input == 'T ':
              sys.exit()
            else:
              third_lst = second_input.split()
              third_string = ''.join(second_input[2:])
              print(third_string)
              interesting_file_list = t_input(list2, third_string)
              invalid_input = False
              if interesting_file_list == []:
                sys.exit()
          elif second_input.startswith('< '):
            fourth_lst = second_input.split()
            number = ''.join(second_input[2:])
            interesting_file_list = less_than_input(number, list2)
            invalid_input = False
            if interesting_file_list == []:
              invalid_input = True
              second_input = input()
          elif second_input.startswith('> '):
            fifth_lst = second_input.split()
            number_2 = ''.join(second_input[2:])
            interesting_file_list = greater_than_equal_to_input(number_2, list2)
            invalid_input = False
            if interesting_file_list == []:
              invalid_input = True
              second_input = input()
      else:
        print('ERROR')
        invalid_input = True
        second_input = input()
    except OSError:
        print('ERROR')
        second_input = input()
        

  third_choice(interesting_file_list)
      
         
def a_input(list2: [Path]) -> [Path]:
  '''Prints all the paths of the files that were interesting from the previous input''' 
  real_final_list = []
  for element in list2:
    if element.is_file():
      real_final_list.append(element)
      print(element)

  return real_final_list

def n_input(list2: [Path], second_path: str) -> [Path]:
  '''Prints all the paths of the files whose filename and extension matches the input'''
  list_files = []
  real_final_list = []
  for element in list2:
    sub_element = str(element)
    string = sub_element[sub_element.rfind('/') + 1:]
    list_files.append(string)
  if second_path in list_files:
    some_list = [i for i, n in enumerate(list_files) if n == second_path]
    for element in some_list:
      real_final_list.append(list2[element])
  for element in real_final_list:
    print(element)
  return real_final_list

def e_input(second_path: str, list2: [Path]) -> [Path]:
  '''Prints all the paths of the files whose extension matches the input'''
  real_final_list = []
  if '.' in second_path:
      list_input = list(second_path)
      del list_input[list_input.index('.')]
      second_path = ''.join(list_input)
              
  list_extension = []
  for element in list2:
      sub_element = str(element)
      string = sub_element[sub_element.rfind('.') + 1:]
      list_extension.append(string)

  if second_path in list_extension:
      some_list = [i for i, n in enumerate(list_extension) if n == second_path]
      for element in some_list:
          real_final_list.append(list2[element])
          print(list2[element])
                
  return real_final_list

def t_input(list2: [Path], third_string: str) -> [Path]:
  '''Prints all the paths of the text files that contain a specific input'''
  real_final_list = [] 
  sub_filelist = []
  final_filelist = []
  for sub_file in list2:
    if sub_file.is_file():
      try:
        if not str(sub_file).endswith('.DS_Store'):
          f = sub_file.open('r')
          message = f.read()
          sub_filelist.append(message)
          f.close()
        else:
          sub_filelist.append('')
      except UnicodeDecodeError:
          pass
      except PermissionError:
          pass


  for string in sub_filelist:
    if third_string in string:
      final_filelist.append(list2[sub_filelist.index(string)])

  for element in final_filelist:
    real_final_list.append(element)
    print(element)
  return real_final_list

def less_than_input(number: str, list2: [Path]) -> [Path]:
  '''Prints all the paths of the files whose file size is less than the threshold inputted'''
  real_final_list = []
  try:
    filelst = []
    first_numberlst = []
    final_numberlst = []
    for sub_file in list2:
      if sub_file.is_file():
          if not str(sub_file).endswith('.DS_Store'):
            filelst.append(sub_file)
    
    for e in filelst:
      stat_e = os.stat(e)
      size = stat_e.st_size
      first_numberlst.append(size)
    if int(number) >= 0:
      for num in first_numberlst:
        if num < int(number):
          real_final_list.append(filelst[first_numberlst.index(num)])
      for element in real_final_list:
        print(element)
      if real_final_list == []:
        sys.exit()
    else:
      print('ERROR')
  except ValueError:
    print('ERROR')
  return real_final_list

def greater_than_equal_to_input(number: str, list2: [Path]) -> [Path]:
  '''Prints all the paths of the files whose file size is greater than and equal to
  the threshold inputted'''
  real_final_list = []
  try:
    filelst = []
    first_numberlst = []
    for sub_file in list2:
      if sub_file.is_file():
          if not str(sub_file).endswith('.DS_Store'):
            filelst.append(sub_file)
    for e in filelst:
      stat_e = os.stat(e)
      size = stat_e.st_size
      first_numberlst.append(size)
    if int(number) >= 0:
      for num in first_numberlst:
        if num >= int(number):
          real_final_list.append(filelst[first_numberlst.index(num)])
      for element in real_final_list:
        print(element)
      if real_final_list == []:
        sys.exit()
    else:
      print('ERROR')
  except ValueError:
    print('ERROR')
  return real_final_list

def third_choice(interesting_file_list: [Path]) -> None:
  '''Contains the while loop for the third input choice F,D,T in order to narrow the search'''
  invalid_input = True
  third_input = input()
  while invalid_input == True:
    try:
      if third_input == 'F':
        f_input(interesting_file_list)
        invalid_input = False
      elif third_input == 'D':
        d_input2(interesting_file_list)
        invalid_input = False
      elif third_input == 'T':
        t_input2(interesting_file_list)
        invalid_input = False
      else:
        invalid_input = True
        print('ERROR')
        third_input = input()
    except FileNotFoundError:
      sys.exit()
    except OSError:
      invalid_input = True
      print('ERROR')
      third_input = input()
      
def f_input(interesting_file_list: [Path]):
  '''Prints the first line of text from text files'''
  for file in interesting_file_list:
    try:
      f = file.open('r')
      message = f.readline()     
      print(message.strip('\n'))
      f.close()
    except UnicodeDecodeError:
      print("NOT TEXT")
    except PermissionError:
      print("NOT TEXT")

def d_input2(interesting_file_list: [Path]):
  '''Creates a duplicate copy of the file and stores it in original directory'''
  for file in interesting_file_list:
    dup_file = str(file / Path('.dup'))
    dup_list = list(dup_file)
    del dup_list[dup_file.rfind('/')]
    dup_real_file = ''.join(dup_list)
  
    shutil.copyfile(file, dup_real_file)

def t_input2(interesting_file_list: [Path]):
  '''Changes last modified timestamp to the current date and time'''
  for file in interesting_file_list:
    stat_info = os.stat(file)
    os.utime(file, None)


if __name__ == '__main__':
  main()
