def create_list_alphabet(n):
    alphabet = []
    for i in range(ord('a'),ord('a')+n):
        alphabet.append(chr(i))
    return alphabet
def dangoli_problem(n):
    alphabet = create_list_alphabet(n)
    output = '' #size 5 -> 11 lines
    for i in range(1,n+1): #line 1 to n (middle) 
        line = '-'*(n-i)*2  #line i contains i characters and i-1 '-' between, 2*(n-i) '-' each side
        for j in range(1,i+1):
            if j != i:
                line += alphabet[-j]
                line += '-'
            else:
                first_half_line = list(line)
                line += alphabet[-j]
                break
        first_half_line.reverse()
        for j in first_half_line:
            line += str(j)
        line += '\n'
        output += line
    other_half = output.split('\n')
    other_half.reverse()
    other_half = other_half[2:]
    for i in other_half:
        output += i
        output += '\n'
    return output
print(dangoli_problem(5))